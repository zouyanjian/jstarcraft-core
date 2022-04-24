package com.jstarcraft.core.codec.thrift.converter;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TField;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TStruct;
import org.apache.thrift.protocol.TType;

import com.jstarcraft.core.codec.specification.ClassDefinition;
import com.jstarcraft.core.utility.StringUtility;

/**
 * 枚举转换器
 * 
 * @author Birdy
 *
 */
public class EnumerationConverter extends ThriftConverter<Object> {

    protected static final TField NULL_MARK = new TField(StringUtility.EMPTY, TType.BYTE, (short) 1);

    @Override
    public Object readValueFrom(ThriftContext context, Type type, ClassDefinition definition) throws IOException, TException {
        TProtocol protocol = context.getProtocol();
        protocol.readStructBegin();
        Object instance;
        TField feild = protocol.readFieldBegin();
        if (NULL_MARK.equals(feild)) {
            instance = null;
        } else {
            Class<?> clazz = definition.getType();
            int ordinal = protocol.readI32();
            instance = clazz.getEnumConstants()[ordinal];
        }
        protocol.readFieldEnd();
        protocol.readFieldBegin();
        protocol.readStructEnd();
        return instance;
    }

    @Override
    public void writeValueTo(ThriftContext context, Type type, ClassDefinition definition, Object instance) throws IOException, TException {
        TProtocol protocol = context.getProtocol();
        protocol.writeStructBegin(new TStruct(definition.getName()));
        if (instance == null) {
            protocol.writeFieldBegin(NULL_MARK);
            protocol.writeFieldEnd();
        } else {
            protocol.writeFieldBegin(new TField(StringUtility.EMPTY, TType.I32, (short) 2));
            Enum<?> enumeration = (Enum<?>) instance;
            int ordinal = enumeration.ordinal();
            protocol.writeI32(ordinal);
            protocol.writeFieldEnd();
        }
        protocol.writeFieldStop();
        protocol.writeStructEnd();
    }

}
