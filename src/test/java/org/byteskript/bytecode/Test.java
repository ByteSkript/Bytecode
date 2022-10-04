package org.byteskript.bytecode;

import org.byteskript.skript.compiler.SimpleSkriptCompiler;
import org.byteskript.skript.runtime.Skript;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class Test {
    
    private static final Skript skript
        = new Skript(new SimpleSkriptCompiler(Bytecode.LIBRARY));
//        = new Skript(new DebugSkriptCompiler(Stream.controller(OutputStream.nullOutputStream()), Bytecode.LIBRARY));
    
    public static void print(String string) {
        System.out.println(string);
    }
    
    public static int test(int i) {
        return i;
    }
    
    @org.junit.Test
    public void all() throws Throwable {
        final URI uri = Test.class.getClassLoader().getResource("tests").toURI();
        final Path path;
        if (uri.getScheme().equals("jar")) {
            final FileSystem system = FileSystems.newFileSystem(uri, Collections.emptyMap());
            path = system.getPath("tests");
        } else {
            path = Paths.get(uri);
        }
        final Skript.Test test = skript.new Test(true);
        test.testDirectory(path);
        final int failure = test.getFailureCount();
        for (final Throwable error : test.getErrors())
            synchronized (this) {
                error.printStackTrace(System.err);
            }
        assert failure < 1 : failure + " tests have failed.";
    }
}
