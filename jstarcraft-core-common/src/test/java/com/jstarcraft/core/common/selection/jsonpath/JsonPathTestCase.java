package com.jstarcraft.core.common.selection.jsonpath;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.jsfr.json.JsonSurfer;
import org.jsfr.json.JsonSurferFastJson;
import org.jsfr.json.JsonSurferGson;
import org.jsfr.json.JsonSurferJackson;
import org.jsfr.json.JsonSurferJsonSimple;
import org.jsfr.json.compiler.JsonPathCompiler;
import org.junit.Assert;
import org.junit.Test;
import org.noear.snack.ONode;

import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JettisonProvider;
import com.jayway.jsonpath.spi.json.JsonOrgJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.json.JsonSmartJsonProvider;
import com.jstarcraft.core.common.io.IoUtility;
import com.jstarcraft.core.utility.StringUtility;

public class JsonPathTestCase {

    @Test
    public void testJayway() {
        JsonProvider[] adapters = new JsonProvider[] { new GsonJsonProvider(),

                new JacksonJsonNodeJsonProvider(),

                new JacksonJsonProvider(),

                new JettisonProvider(),

                new JsonOrgJsonProvider(),

                new JsonSmartJsonProvider() };
        // TODO 因为TapestryJsonProvider要求根元素必须为{},所以独立测试.
        try (InputStream stream = JsonPathTestCase.class.getResourceAsStream("jsonpath.json"); DataInputStream buffer = new DataInputStream(stream)) {
            String json = IoUtility.toString(stream, StringUtility.CHARSET);
            for (JsonProvider adapter : adapters) {
                Object root = adapter.parse(json);
                JaywayJsonPathSelector selector;

                selector = new JaywayJsonPathSelector("$[0]", adapter);
                Assert.assertEquals(1, selector.selectContent(root).size());

                selector = new JaywayJsonPathSelector("$[0:3]", adapter);
                Assert.assertEquals(3, selector.selectContent(root).size());

                selector = new JaywayJsonPathSelector("$[-3:0]", adapter);
                Assert.assertEquals(3, selector.selectContent(root).size());

                selector = new JaywayJsonPathSelector("$..name", adapter);
                Assert.assertEquals(3, selector.selectContent(root).size());

                selector = new JaywayJsonPathSelector("$[?(@.age > 10)]", adapter);
                Assert.assertEquals(2, selector.selectContent(root).size());

                selector = new JaywayJsonPathSelector("$[?(@.age < 10)]", adapter);
                Assert.assertEquals(1, selector.selectContent(root).size());

                selector = new JaywayJsonPathSelector("$[?(@.sex == true)]", adapter);
                Assert.assertEquals(2, selector.selectContent(root).size());

                selector = new JaywayJsonPathSelector("$[?(@.sex == false)]", adapter);
                Assert.assertEquals(1, selector.selectContent(root).size());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalArgumentException(exception);
        }
    }

    @Test
    public void testJsonSurfer() {
        JsonSurfer[] adapters = new JsonSurfer[] { JsonSurferGson.INSTANCE,

                JsonSurferJackson.INSTANCE,

                JsonSurferJsonSimple.INSTANCE,

                JsonSurferFastJson.INSTANCE };
        try (InputStream stream = JsonPathTestCase.class.getResourceAsStream("jsonpath.json"); DataInputStream buffer = new DataInputStream(stream)) {
            String json = IoUtility.toString(stream, StringUtility.CHARSET);
            for (JsonSurfer adapter : adapters) {
                Collection<Object> collection = new ArrayList<>();

                collection.clear();
                adapter.configBuilder().bind(JsonPathCompiler.compile("$[0]"), (value, context) -> {
                    collection.add(value);
                }).buildAndSurf(json);
                Assert.assertEquals(1, collection.size());

                collection.clear();
                adapter.configBuilder().bind(JsonPathCompiler.compile("$[0:3]"), (value, context) -> {
                    collection.add(value);
                }).buildAndSurf(json);
                Assert.assertEquals(3, collection.size());

//                Assert.assertEquals(3, adapter.collectAll(json, "$[-3:0]").size());

                collection.clear();
                adapter.configBuilder().bind(JsonPathCompiler.compile("$..name"), (value, context) -> {
                    collection.add(value);
                }).buildAndSurf(json);
                Assert.assertEquals(3, collection.size());

                collection.clear();
                adapter.configBuilder().bind(JsonPathCompiler.compile("$[?(@.age > 10)]"), (value, context) -> {
                    collection.add(value);
                }).buildAndSurf(json);
                Assert.assertEquals(2, collection.size());

                collection.clear();
                adapter.configBuilder().bind(JsonPathCompiler.compile("$[?(@.age < 10)]"), (value, context) -> {
                    collection.add(value);
                }).buildAndSurf(json);
                Assert.assertEquals(1, collection.size());

                collection.clear();
                adapter.configBuilder().bind(JsonPathCompiler.compile("$[?(@.sex == true)]"), (value, context) -> {
                    collection.add(value);
                }).buildAndSurf(json);
                Assert.assertEquals(2, collection.size());

                collection.clear();
                adapter.configBuilder().bind(JsonPathCompiler.compile("$[?(@.sex == false)]"), (value, context) -> {
                    collection.add(value);
                }).buildAndSurf(json);
                Assert.assertEquals(1, collection.size());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalArgumentException(exception);
        }
    }

    @Test
    public void testSnack3() {
        try (InputStream stream = JsonPathTestCase.class.getResourceAsStream("jsonpath.json"); DataInputStream buffer = new DataInputStream(stream)) {
            String json = IoUtility.toString(stream, StringUtility.CHARSET);
            ONode root = ONode.load(json);
            SnackJsonPathSelector selector;

            selector = new SnackJsonPathSelector("$[0]");
            Assert.assertEquals(1, selector.selectContent(root).size());

            selector = new SnackJsonPathSelector("$[0:3]");
            Assert.assertEquals(3, selector.selectContent(root).size());

            selector = new SnackJsonPathSelector("$[-3:0]");
            Assert.assertEquals(3, selector.selectContent(root).size());

            selector = new SnackJsonPathSelector("$..name");
            Assert.assertEquals(3, selector.selectContent(root).size());

            selector = new SnackJsonPathSelector("$[?(age > 10)]");
            Assert.assertEquals(2, selector.selectContent(root).size());

            selector = new SnackJsonPathSelector("$[?(age < 10)]");
            Assert.assertEquals(1, selector.selectContent(root).size());

            selector = new SnackJsonPathSelector("$[?(sex == 'true')]");
            Assert.assertEquals(2, selector.selectContent(root).size());

            selector = new SnackJsonPathSelector("$[?(sex == 'false')]");
            Assert.assertEquals(1, selector.selectContent(root).size());
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception);
        }
    }

}
