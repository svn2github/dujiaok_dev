For DEV
--------------
����Ӧ��rpm����Ͱ�װ�Ľű���ܣ��������˽�rpm����ʹ�á���򵥵ķ���������ѡһ���Ѿ�ʵ����rpm�ķ�֧��diffһ�¾ͺ�����ˣ�
�ܹ��ż�ʮ�д��룬��è����һ�㲻���
   xtool      http://svn.alibaba-inc.com/repos/ali_cn/bops/xtool/branches/20100328_rpm_chendf_dev/bundle/rpm 
   exodus    http://svn.alibaba-inc.com/repos/ali_cn/olps/exodus2/branches/20100329_rpm_chendf_DEV/bundle/rpm
   myalibaba  http://svn.alibaba-inc.com/repos/ali_cn/olps/myalibaba/branches/myalibaba_maven_20100406/bundle/rpm 
   quan      http://svn.alibaba-inc.com/repos/ali_cn/bizcom/quan/branches/quan_maven_20100323/bundle/rpm 

�ֹ���rpm�ķ�����
1. �����в���������ļ����ɵ���Ŀ��Ŀ¼<project>�µ�target/Ŀ¼
   ** ͨ����Ҫ�޸�deploy/pom.xml����web-deploy.tgz��Ϊ��jar��������web-deploy.jar����
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
2. �½������ű���app-config.sh, app-deploy.sh, env.sh.vm���ŵ�<project>/bundle/rpmĿ¼��
   ** ע��app-config.sh, app-deploy.sh, env.sh.vm��Ҫ��unix�ļ���ʽ����\n���ж�����΢���\r\n���У�������dos2unixת��
   ** ��ȷ��auto-config.xml���Ὣ�ļ����ɵ���ͬ��Ŀ¼�£���jboss_server��
   ** app-deploy.sh������Ϊ������ѡ�Ļص�������deploy_war, deploy_template, deploy_task���ֱ���������war����ģ���task
   ** ע������auto-config���ɵ��ļ�������jar������ģ���jar���ǲ�����Ȩ�޵ģ����Դ����Ҫ��app-deploy.sh����
      �����Ȩ�޼���ȥ������������auto-config��Ȩ�����ã���chmod 755 $WEBAPP_HOME/bin/*
3. ��bundle/rpmĿ¼��svn:externals����frameworkĿ¼��ע�����������һ���㣩��
   [~/exodus2/bundle/rpm]$ svn propset svn:externals "framework http://svn.alibaba-inc.com/repos/ali_cn/misc/scm-scripts/rpm/framework" .
4. ����Ŀ��Ŀ¼<project>�·�һ��make-rpm.sh������Ϊ
cd `dirname $0`
BASE=`pwd`
cp $BASE/antx.properties $BASE/target/
ant -DappName=exodus2 -DversionFormat=yyyyMMdd -Dbasedir=$BASE -f $BASE/bundle/rpm/framework/build.xml

����rpm�ķ�����
1. ����Ŀ��Ŀ¼<project>��ִ�� mvn install -Denv=release�����<project>/target�����Ƿ��Ѿ�������������war/jar��
2. ������Լ���linux������ִ�У���ִ��type rpm-build���Ƿ��Ѿ���װ��rpm-build�����û�еĻ���ִ��yum install rpm-build��װ��
3. ��linux��ִ��<project>/make-rpm.sh������<project>/target/rpm/RPMS/noarch��������rpm�ļ�
4. ����http://svn.alibaba-inc.com/repos/ali_cn/misc/scm-scripts/rpm/yuminstall �ű����ŵ�PATH�У���/usr/bin��
5. �ڵ�ǰĿ¼��׼��antx.properties�ļ���ִ��yuminstall <project>/target/rpm/RPMS/noarch/<rpm>��
   Ӧ�û��Զ���װ��antx.properties��ָ����·������exodus2.deployhome, exodus2.taskhome��

rpm��װִ�й��̣�
1. �Զ���target/Ŀ¼��ѹ����ǰĿ¼
2. ִ��app-config.sh�����ã�����auto_config������
3. �Զ���env.sh.vm��auto-config����ִ��env.sh
4. ִ��app-deploy.sh�����𣨵���deploy������

����/����˵����
auto_config pkg        �԰�ִ��antxconfig���ж��Ƿ�ɹ����
deploy pkg, dest       �԰��Զ���ѹ������������ͬ����destĿ¼
$TARGET                rpm��ѹ���target/Ŀ¼


For OPS
--------------
ִ��install.sh���������в����԰�װ���и�ϸ���ȵĿ��ƣ�
--non-interactive:  ��auto-config���������ȫʱ��ѯ���û������������˳�
--config:           ִ��auto-config
--deploy-war:       ִ��app-deploy.sh�е�deploy_war������war��
--deploy-template:  ִ��app-deploy.sh�е�deploy_template������web-deployĿ¼
--deploy-task:      ִ��app-deploy.sh�е�deploy_task������task
����4���������ȫ����ָ������Ĭ��ȫ��ִ�У�����ִֻ��ָ���Ĳ��衣

��1����Ԥ��������ִ��auto-config������webӦ��
yuminstall <rpm> -x       #ֻ��ѹ����ִ��install.sh
#ִ��auto-config������war����ģ��
./target/rpm/install.sh --non-interactive --config --deploy-war --deploy-template

��2: ��task������ʽtask��
rsync -avzq --delete ./target admin@app-task01/home/admin  #��targetĿ¼���Ƶ�task��
ssh admin@app-task01                                       #��¼��task��
./target/rpm/install.sh --deploy-task                      #ֱ�Ӳ���task

