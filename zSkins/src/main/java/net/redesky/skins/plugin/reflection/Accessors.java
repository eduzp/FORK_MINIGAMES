package net.redesky.skins.plugin.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Accessors {
   private Accessors() {
   }

   public static void setAccessible(Field field) {
      if (!field.isAccessible()) {
         field.setAccessible(true);
      }

      if (field.getModifiers() != (field.getModifiers() & -17)) {
         getField(Field.class, "modifiers").set(field, field.getModifiers() & -17);
      }

   }

   public static void setFieldValue(Field field, Object target, Object value) {
      (new FieldAccessor(field, true)).set(target, value);
   }

   public static Object getFieldValue(Field field, Object target) {
      return (new FieldAccessor(field, true)).get(target);
   }

   public static FieldAccessor<Object> getField(Class clazz, int index) {
      return getField(clazz, index, (Class)null);
   }

   public static FieldAccessor<Object> getField(Class clazz, String fieldName) {
      return getField(clazz, fieldName, (Class)null);
   }

   public static <T> FieldAccessor<T> getField(Class clazz, int index, Class<T> fieldType) {
      return getField(clazz, (String)null, index, fieldType);
   }

   public static <T> FieldAccessor<T> getField(Class clazz, String fieldName, Class<T> fieldType) {
      return getField(clazz, fieldName, 0, fieldType);
   }

   public static <T> FieldAccessor<T> getField(Class clazz, String fieldName, int index, Class<T> fieldType) {
      Field[] var5 = clazz.getDeclaredFields();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Field field = var5[var7];
         if ((fieldName == null || fieldName.equals(field.getName())) && (fieldType == null || fieldType.equals(field.getType())) && index-- == 0) {
            return new FieldAccessor(field, true);
         }
      }

      String message = " with index " + index;
      if (fieldName != null) {
         message = message + " and name " + fieldName;
      }

      if (fieldType != null) {
         message = message + " and type " + fieldType;
      }

      throw new IllegalArgumentException("Cannot find field " + message);
   }

   public static <T> MethodAccessor<T> getMethod(Class clazz, String methodName) {
      return getMethod(clazz, (Class)null, methodName, (Class[])null);
   }

   public static <T> MethodAccessor<T> getMethod(Class clazz, int index) {
      return getMethod(clazz, (Class)null, index, (Class[])null);
   }

   public static MethodAccessor getMethod(Class clazz, String methodName, Class... parameters) {
      return getMethod(clazz, (Class)null, methodName, parameters);
   }

   public static <T> MethodAccessor<T> getMethod(Class clazz, int index, Class... parameters) {
      return getMethod(clazz, (Class)null, index, parameters);
   }

   public static <T> MethodAccessor<T> getMethod(Class clazz, Class<T> returnType, String methodName, Class... parameters) {
      return getMethod(clazz, 0, returnType, methodName, parameters);
   }

   public static <T> MethodAccessor<T> getMethod(Class clazz, Class<T> returnType, int index, Class... parameters) {
      return getMethod(clazz, index, returnType, (String)null, parameters);
   }

   public static <T> MethodAccessor<T> getMethod(Class clazz, int index, Class<T> returnType, String methodName, Class... parameters) {
      Method[] var6 = clazz.getDeclaredMethods();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Method method = var6[var8];
         if ((methodName == null || method.getName().equals(methodName)) && (returnType == null || method.getReturnType().equals(returnType)) && (parameters == null || Arrays.equals(method.getParameterTypes(), parameters)) && index-- == 0) {
            return new MethodAccessor(method, true);
         }
      }

      String message = " with index " + index;
      if (methodName != null) {
         message = message + " and name " + methodName;
      }

      if (returnType != null) {
         message = message + " and returntype " + returnType;
      }

      if (parameters != null && parameters.length > 0) {
         message = message + " and parameters " + Arrays.asList(parameters);
      }

      throw new IllegalArgumentException("Cannot find method " + message);
   }

   public static ConstructorAccessor getConstructor(Class clazz, int index) {
      return getConstructor(clazz, index, (Class[])null);
   }

   public static ConstructorAccessor getConstructor(Class clazz, Class... parameters) {
      return getConstructor(clazz, 0, parameters);
   }

   public static ConstructorAccessor getConstructor(Class clazz, int index, Class... parameters) {
      Constructor[] var4 = clazz.getDeclaredConstructors();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Constructor<?> constructor = var4[var6];
         if ((parameters == null || Arrays.equals(constructor.getParameterTypes(), parameters)) && index-- == 0) {
            return new ConstructorAccessor(constructor, true);
         }
      }

      throw new IllegalArgumentException("Cannot find constructor for class " + clazz + " with index " + index);
   }
}
