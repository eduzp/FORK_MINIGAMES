package net.redesky.skins.plugin.reflection;

import java.lang.reflect.Method;

public class MethodAccessor<TReturn> {
   private Method handle;

   public MethodAccessor(Method method) {
      this(method, false);
   }

   public MethodAccessor(Method method, boolean forceAccess) {
      this.handle = method;
      if (forceAccess) {
         method.setAccessible(true);
      }

   }

   public TReturn invoke(Object target, Object... args) {
      try {
         return this.handle.invoke(target, args);
      } catch (ReflectiveOperationException var4) {
         throw new RuntimeException("Cannot invoke method.", var4);
      }
   }

   public boolean hasMethod(Object target) {
      return target != null && this.handle.getDeclaringClass().equals(target.getClass());
   }
}
