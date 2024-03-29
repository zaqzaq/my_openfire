/**
 * $RCSfile$
 * $Revision: 11291 $
 * $Date: 2009-09-30 18:17:14 +0800 (Wed, 30 Sep 2009) $
 *
 * Copyright (C) 2004-2008 Jive Software. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jivesoftware.util;

import junit.framework.TestCase;

/**
 * <p>Test the writing of dom4j documents using the XPP serializer.</p>
 *
 * @author Iain Shigeoka
 */
public class XPPWriterTest extends TestCase {
    /**
     * <p>Create a new test with the given name.</p>
     *
     * @param name The name of the test
     */
    public XPPWriterTest(String name){
        super(name);
    }

    /**
     * <p>Run a standard config document through a round trip and compare.</p>
     */
    public void testRoundtrip(){
        // NOTE: disabling this test case until we get resources working again.
        /*
        try {
            Document doc = XPPReader.parseDocument(new FileReader("../conf/openfire.xml"),this.getClass());
            XPPWriter.write(doc, new FileWriter("../conf/xmpp_writer_test_copy.xml"));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        */
    }
}
