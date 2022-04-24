package com.jstarcraft.core.codec.xml;

import java.util.HashMap;
import java.util.Map;

import com.jstarcraft.core.codec.ContentCodec;
import com.jstarcraft.core.codec.ContentCodecTestCase;
import com.jstarcraft.core.codec.specification.CodecDefinition;

public class XmlContentCodecTestCase extends ContentCodecTestCase {

    @Override
    protected ContentCodec getContentCodec(CodecDefinition protocolDefinition) {
        XmlContentCodec codec = new XmlContentCodec(protocolDefinition);
        return codec;
    }

    @Override
    public void testArray() throws Exception {
        super.testArray();

        Object[] array = new Object[] { 0, null, "string" };
        testConvert(Object[].class, array);
    }

    @Override
    public void testComplex() throws Exception {
        super.testComplex();

        Map<Object, Object> map = new HashMap<>();
        map.put("integer", 0);
        map.put("null", null);
        map.put("string", "string");
        testConvert(HashMap.class, map);
    }

}
