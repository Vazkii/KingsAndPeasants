/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Kings and Peasants Mod.
 *
 * Kings and Peasants is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [13 Jul 2013, 11:12:09 (GMT)]
 */
package vazkii.kap.util.nbt;

import java.lang.reflect.Field;

import net.minecraft.nbt.NBTTagCompound;

public final class NBTManager {

	public static void loadType(NBTTagCompound cmp, Object o) {
		Class type = o.getClass();

		for(Field f : type.getFields()) {
			NBTManaged managed = f.getAnnotation(NBTManaged.class);
			if(managed != null) {
				try {
					String tag = managed.value();

					if(cmp.hasKey(tag))
						loadManagedValue(cmp, tag, f, o);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void writeType(NBTTagCompound cmp, Object o) {
		Class type = o.getClass();

		for(Field f : type.getFields()) {
			NBTManaged managed = f.getAnnotation(NBTManaged.class);
			if(managed != null) {
				try {
					String tag = managed.value();
					f.get(o);

					writeManagedValue(cmp, tag, o);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void writeManagedValue(NBTTagCompound cmp, String tag, Object value) {
		if(value instanceof Byte)
			cmp.setByte(tag, (Byte) value);
		else if(value instanceof Short)
			cmp.setShort(tag, (Short) value);
		else if(value instanceof Integer)
			cmp.setInteger(tag, (Integer) value);
		else if(value instanceof Long)
			cmp.setLong(tag, (Long) value);
		else if(value instanceof Float)
			cmp.setFloat(tag, (Float) value);
		else if(value instanceof Double)
			cmp.setDouble(tag, (Double) value);
		else if(value instanceof byte[])
			cmp.setByteArray(tag, (byte[]) value);
		else if(value instanceof String)
			cmp.setString(tag, (String) value);
		else if(value instanceof int[])
			cmp.setIntArray(tag, (int[]) value);
		else {
			NBTTagCompound cmp1 = new NBTTagCompound();
			writeType(cmp1, value);
			cmp.setCompoundTag(tag, cmp1);
		}
	}

	private static void loadManagedValue(NBTTagCompound cmp, String tag, Field field, Object type) throws Exception {
		Object obj = field.get(type);

		if(obj instanceof Byte)
			field.set(type, cmp.getByte(tag));
		else if(obj instanceof Short)
			field.set(type, cmp.getShort(tag));
		else if(obj instanceof Integer)
			field.set(type, cmp.getInteger(tag));
		else if(obj instanceof Long)
			field.set(type, cmp.getLong(tag));
		else if(obj instanceof Float)
			field.set(type, cmp.getFloat(tag));
		else if(obj instanceof Double)
			field.set(type, cmp.getDouble(tag));
		else if(obj instanceof byte[])
			field.set(type, cmp.getByteArray(tag));
		else if(obj instanceof String)
			field.set(type, cmp.getString(tag));
		else if(obj instanceof int[])
			field.set(type, cmp.getIntArray(tag));
		else {
			NBTTagCompound cmp1 = cmp.getCompoundTag(tag);
			loadType(cmp1, obj);
		}
	}
}