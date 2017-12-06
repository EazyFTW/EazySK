package com.eazyftw.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.event.*;

public class ExprServerVersion extends SimpleExpression<String> {

    @Nullable
    public String[] get(final Event event) {
        return new String[] { Bukkit.getBukkitVersion() };
    }

    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final SkriptParser.ParseResult p) {
        return true;
    }

    public Class<? extends String> getReturnType() {
        return String.class;
    }

    public boolean isSingle() {
        return true;
    }
    public String toString(@Nullable final Event e, final boolean b) {
        return this.toString();
    }
}
