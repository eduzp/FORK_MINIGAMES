package mc.twilight.cosmeticos.utils.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface EntitySize {
  float width() default 0.5F;
  
  float height() default 0.5F;
}
