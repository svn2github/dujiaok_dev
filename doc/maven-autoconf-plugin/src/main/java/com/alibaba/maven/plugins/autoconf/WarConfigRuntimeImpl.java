package com.alibaba.maven.plugins.autoconf;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.antx.config.ConfigResource;
import com.alibaba.antx.config.ConfigRuntimeImpl;
import com.alibaba.antx.config.entry.ConfigEntry;
import com.alibaba.antx.util.PatternSet;

@SuppressWarnings("unchecked")
public class WarConfigRuntimeImpl extends ConfigRuntimeImpl{

    public WarConfigRuntimeImpl() {
        super();
    }

    public WarConfigRuntimeImpl(InputStream inputStream, OutputStream outStream,
                                OutputStream errStream, String charset) {
        super(inputStream, outStream, errStream, charset);
    }

    private static final String WAR_CONFIG_PATTERN = "WEB-INF/classes/META-INF/**/auto-config.xml";
    
    @Override
    public List scan(boolean includeEmptyEntries) {
        File[] destFiles = getDestFiles();
        List entries = new ArrayList(destFiles.length);

        for (int i = 0; i < destFiles.length; i++) {
            File destFile = destFiles[i];
            ConfigEntry entry = getConfigEntryFactory().create(new ConfigResource(destFile));
            if (destFile.getName().endsWith(".war")) {
                PatternSet oldPatternSet = entry.getDescriptorPatterns();
                String[] includes = oldPatternSet.getIncludes();
                String[] excludes = oldPatternSet.getExcludes();
                includes = copyOf(includes, includes.length+1);
                includes[includes.length-1] = WAR_CONFIG_PATTERN;
                
                PatternSet newPatterns = new PatternSet(includes,excludes);
                entry.setDescriptorPatterns(newPatterns);
            }

            // added by gang.lvg
            PatternSet patternSet = getDescriptorPatterns();
            if ( patternSet != null ) {
                entry.setDescriptorPatterns(
                        mergePatternSet( patternSet, entry.getDescriptorPatterns() ) );
            }

            patternSet = getPackagePatterns();
            if ( patternSet != null ) {
                entry.setPackagePatterns(
                        mergePatternSet( patternSet, entry.getPackagePatterns() ) );
            }

            entry.scan();

            if (includeEmptyEntries || !entry.isEmpty()) {
                entries.add(entry);
            }
        }

        return entries;
    }

    private PatternSet mergePatternSet( PatternSet src, PatternSet dest ) {

        if ( src == null ) {
            src = new PatternSet();
        }

        if ( dest == null ) {
            dest = new PatternSet();
        }

        List<String> includes = new ArrayList<String>();
        List<String> excludes = new ArrayList<String>();

        String[] array;

        if ( ( array = src.getIncludes() ) != null ) {
            includes.addAll( Arrays.asList( array ) );
        }
        if ( ( array = dest.getIncludes() ) != null ) {
            includes.addAll( Arrays.asList( array ) );
        }

        if ( ( array = src.getExcludes() ) != null ) {
            excludes.addAll( Arrays.asList( array ) );
        }

        if ( ( array = dest.getExcludes() ) != null ) {
            excludes.addAll( Arrays.asList( array ) );
        }

        return new PatternSet(
                includes.toArray( new String[includes.size()] ),
                excludes.toArray( new String[excludes.size()] ) );
    }

    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }
    public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        T[] copy = ((Object)newType == (Object)Object[].class)
            ? (T[]) new Object[newLength]
            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }
    
}
