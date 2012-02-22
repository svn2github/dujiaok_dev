For DEV
--------------
这是应用rpm打包和安装的脚本框架，您无需了解rpm即可使用。最简单的方法，是任选一个已经实现了rpm的分支，diff一下就很清楚了，
总共才几十行代码，照猫画虎一般不会错：
   xtool      http://svn.alibaba-inc.com/repos/ali_cn/bops/xtool/branches/20100328_rpm_chendf_dev/bundle/rpm 
   exodus    http://svn.alibaba-inc.com/repos/ali_cn/olps/exodus2/branches/20100329_rpm_chendf_DEV/bundle/rpm
   myalibaba  http://svn.alibaba-inc.com/repos/ali_cn/olps/myalibaba/branches/myalibaba_maven_20100406/bundle/rpm 
   quan      http://svn.alibaba-inc.com/repos/ali_cn/bizcom/quan/branches/quan_maven_20100323/bundle/rpm 

手工改rpm的方法：
1. 把所有部署所需的文件生成到项目根目录<project>下的target/目录
   ** 通常需要修改deploy/pom.xml，将web-deploy.tgz改为用jar打包，变成web-deploy.jar，如
       <jar destfile="${release_directory}/web-deploy.jar">
                <zipfileset dir="${project.basedir}/conf/META-INF" prefix="META-INF"/>
                <zipfileset dir="${project.basedir}">
                          <include name="htdocs/**"/>
                          <include name="templates/**"/>
                          <include name="conf/**"/>
                          <exclude name="conf/META-INF/**"/>
                </zipfileset>
                <zipfileset dir="${project.basedir}" filemode="755">
                          <include name="bin/**"/>
                </zipfileset>
       </jar>
2. 新建三个脚本：app-config.sh, app-deploy.sh, env.sh.vm，放到<project>/bundle/rpm目录中
   ** 注意app-config.sh, app-deploy.sh, env.sh.vm都要用unix文件格式（用\n换行而不用微软的\r\n换行），可用dos2unix转换
   ** 请确保auto-config.xml不会将文件生成到非同步目录下（如jboss_server）
   ** app-deploy.sh的内容为三个可选的回调函数：deploy_war, deploy_template, deploy_task，分别用来部署war包、模板和task
   ** 注意现在auto-config生成的文件都是在jar包里面的，而jar包是不保存权限的，所以大家需要在app-deploy.sh里面
      把这个权限加上去，不能依赖于auto-config的权限设置，如chmod 755 $WEBAPP_HOME/bin/*
3. 在bundle/rpm目录中svn:externals引入framework目录（注意命令最后有一个点）：
   [~/exodus2/bundle/rpm]$ svn propset svn:externals "framework http://svn.alibaba-inc.com/repos/ali_cn/misc/scm-scripts/rpm/framework" .
4. 在项目根目录<project>下放一个make-rpm.sh，内容为
cd `dirname $0`
BASE=`pwd`
cp $BASE/antx.properties $BASE/target/
ant -DappName=exodus2 -DversionFormat=yyyyMMdd -Dbasedir=$BASE -f $BASE/bundle/rpm/framework/build.xml

生成rpm的方法：
1. 在项目根目录<project>下执行 mvn install -Denv=release，检查<project>/target下面是否已经生成了期望的war/jar包
2. 如果在自己的linux机器上执行，请执行type rpm-build看是否已经安装了rpm-build。如果没有的话，执行yum install rpm-build安装。
3. 在linux下执行<project>/make-rpm.sh，将在<project>/target/rpm/RPMS/noarch下面生成rpm文件
4. 下载http://svn.alibaba-inc.com/repos/ali_cn/misc/scm-scripts/rpm/yuminstall 脚本，放到PATH中（如/usr/bin）
5. 在当前目录中准备antx.properties文件，执行yuminstall <project>/target/rpm/RPMS/noarch/<rpm>，
   应用会自动安装到antx.properties中指定的路径（如exodus2.deployhome, exodus2.taskhome）

rpm安装执行过程：
1. 自动将target/目录解压到当前目录
2. 执行app-config.sh做配置（调用auto_config函数）
3. 自动对env.sh.vm做auto-config，并执行env.sh
4. 执行app-deploy.sh做部署（调用deploy函数）

函数/变量说明：
auto_config pkg        对包执行antxconfig并判断是否成功完成
deploy pkg, dest       对包自动解压并将其中内容同步到dest目录
$TARGET                rpm解压后的target/目录


For OPS
--------------
执行install.sh可以用下列参数对安装进行更细粒度的控制：
--non-interactive:  当auto-config发现配置项不全时不询问用户，立即报错退出
--config:           执行auto-config
--deploy-war:       执行app-deploy.sh中的deploy_war来部署war包
--deploy-template:  执行app-deploy.sh中的deploy_template来部署web-deploy目录
--deploy-task:      执行app-deploy.sh中的deploy_task来部署task
上述4个参数如果全部不指定，则默认全部执行；否则只执行指定的步骤。

例1：在预发布机上执行auto-config并部署web应用
yuminstall <rpm> -x       #只解压，不执行install.sh
#执行auto-config并部署war包和模板
./target/rpm/install.sh --non-interactive --config --deploy-war --deploy-template

例2: 将task部署到正式task机
rsync -avzq --delete ./target admin@app-task01/home/admin  #将target目录复制到task机
ssh admin@app-task01                                       #登录到task机
./target/rpm/install.sh --deploy-task                      #直接部署task

