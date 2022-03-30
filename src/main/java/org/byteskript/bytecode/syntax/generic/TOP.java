package org.byteskript.bytecode.syntax.generic;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.SimpleExpression;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class TOP extends SimpleExpression {
    
    public TOP() {
        super(Bytecode.LIBRARY, StandardElements.EXPRESSION, "top");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.equals("top")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
    }
    
}
