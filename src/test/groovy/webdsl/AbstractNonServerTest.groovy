/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package webdsl

import groovy.xml.StreamingMarkupBuilder
import junit.framework.TestCase

abstract class AbstractNonServerTest extends TestCase {
  private contents

  def html(String contents) {
    this.contents = contents
  }

  def html(Closure closure) {
    contents = new StreamingMarkupBuilder().bind(closure).toString()
  }

  def webdsl(Closure closure) {
    WebDsl webDsl = new WebPageDslBuilder()
        .defaultUrl("http://localhost/test.html")
        .defaultContents(contents)
        .build()

    webDsl.do closure
  }

  //    final List<NameValuePair> expectedParameters = Collections.emptyList();
  //    final MockWebConnection webConnection = getMockConnection(secondPage);
  //
  //    assertEquals("url", "http://www.foo2.com/", secondPage.getWebResponse().getWebRequest().getUrl());
  //    assertSame("method", HttpMethod.GET, webConnection.getLastMethod());
  //    Assert.assertEquals("parameters", expectedParameters, webConnection.getLastParameters());
  //    assertNotNull(secondPage);
}