package org.byteskript.bytecode.syntax.constant;

import mx.kenzie.foundation.MethodBuilder;
import mx.kenzie.foundation.WriteInstruction;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class LDC extends Effect {
    
    public LDC() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "ldc %Object%");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.startsWith("ldc ")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void preCompile(Context context, Pattern.Match match) throws Throwable {
        context.getCompileCurrent().disableChildren();
        super.preCompile(context, match);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final Object value = context.getCompileCurrent().nested()[0].getLiteralValue();
        final MethodBuilder method = context.getMethod();
        assert method != null;
        if (value == null) method.writeCode(WriteInstruction.pushNull());
        else method.writeCode(WriteInstruction.loadConstant(value));
        context.setState(CompileState.CODE_BODY);
    }
    
}
