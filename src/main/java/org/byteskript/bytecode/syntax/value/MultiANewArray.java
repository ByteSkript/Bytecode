package org.byteskript.bytecode.syntax.value;

import mx.kenzie.foundation.MethodBuilder;
import mx.kenzie.foundation.Type;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.ElementTree;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.error.ScriptCompileError;
import org.byteskript.skript.lang.element.StandardElements;

public class MultiANewArray extends Effect {
    
    public MultiANewArray() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "multianewarray %Type%, %Integer%");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.startsWith("new ")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void preCompile(Context context, Pattern.Match match) throws Throwable {
        context.getCompileCurrent().disableChildren();
        super.preCompile(context, match);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final ElementTree[] nested = context.getCompileCurrent().nested();
        final Type owner = nested[0].match().meta();
        final Integer value = context.getCompileCurrent().nested()[0].getLiteralValue();
        if (value == null) throw new ScriptCompileError(context.lineNumber(), "No array dimensions provided.");
        final MethodBuilder method = context.getMethod();
        assert method != null;
        method.writeCode((writer, visitor) -> visitor.visitMultiANewArrayInsn(owner.internalName(), value));
        context.setState(CompileState.CODE_BODY);
    }
}
