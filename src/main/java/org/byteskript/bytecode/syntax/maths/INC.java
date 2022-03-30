package org.byteskript.bytecode.syntax.maths;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.error.ScriptCompileError;
import org.byteskript.skript.lang.element.StandardElements;

public class INC extends Effect {
    
    public INC() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "iinc %Integer%");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.endsWith("add")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void preCompile(Context context, Pattern.Match match) throws Throwable {
        context.getCompileCurrent().disableChildren();
        super.preCompile(context, match);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final Integer value = context.getCompileCurrent().nested()[0].getLiteralValue();
        if (value == null) throw new ScriptCompileError(context.lineNumber(), "No variable index provided.");
        final MethodBuilder method = context.getMethod();
        assert method != null;
        method.writeCode((writer, visitor) -> visitor.visitIincInsn(value, 132));
        context.setState(CompileState.CODE_BODY);
    }
    
}
