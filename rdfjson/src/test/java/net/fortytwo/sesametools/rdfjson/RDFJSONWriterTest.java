package net.fortytwo.sesametools.rdfjson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openrdf.model.vocabulary.OWL;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.rio.RDFWriter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Tests the RDF/JSON writer by way of the RDF/JSON parser.
 *
 * User: josh
 * Date: Dec 21, 2010
 * Time: 5:26:27 PM
 */
public class RDFJSONWriterTest extends RDFJSONTestBase {
    public void testAll() throws Exception {
        JSONObject j;
        JSONArray values;
        JSONArray contexts;

        j = parseAndWrite("example1.json");
        JSONObject a = j.getJSONObject(ARTHUR.toString());
        values = a.getJSONArray(RDF.TYPE.toString());
        assertEquals(2, values.length());
        assertEquals("uri", values.getJSONObject(0).getString("type"));
        assertEquals("uri", values.getJSONObject(1).getString("type"));
        JSONObject t = values.getJSONObject(0);
        if (FOAF.PERSON.toString().equals(t.getString("value"))) {
            t = values.getJSONObject(1);
        }
        //assertEquals(FOAF.PERSON.toString(), values.getJSONObject(0).getString("value"));
        assertEquals(OWL.NAMESPACE + "Thing", t.getString("value"));
        contexts = t.getJSONArray("graphs");
        assertEquals(2, contexts.length());
        assertTrue("null".equals(contexts.getString(0)) || "null".equals(contexts.getString(1)));
        values = a.getJSONArray(FOAF.KNOWS.toString());
        assertEquals(1, values.length());
        JSONObject f = values.getJSONObject(0);
        assertEquals("bnode", f.getString("type"));

        //j = parseAndWrite("example0.json");
    }

    private JSONObject parseAndWrite(final String fileName) throws Exception {
        RDFJSONParser p = new RDFJSONParser();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            RDFWriter w = new RDFJSONWriter(bos);
            p.setRDFHandler(w);
            InputStream in = RDFJSONTestBase.class.getResourceAsStream(fileName);
            try {
                p.parse(in, BASE_URI);
                return new JSONObject(new String(bos.toByteArray()));
            } finally {
                in.close();
            }
        } finally {
            bos.close();
        }
    }
}
