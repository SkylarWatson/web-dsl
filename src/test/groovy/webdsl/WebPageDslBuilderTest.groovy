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

import org.junit.Test


class WebPageDslBuilderTest {

  @Test
  void test_defaultUrl_defaults_when_not_specified() {
    WebDsl dsl = new WebPageDslBuilder()
        .defaultContents('<h1>hello</h1> <a id="big" href="general/bigerror">Big Error</a>')
        .setResponseFor('http://localhost/general/bigerror', '<h1>big error</h1>', 'text/html')
        .setResponseFor('general/littleerror', '<h1>little error</h1>', 'text/html')
        .build()

    dsl.$('#big').click()
    assert dsl.$('h1').text == 'big error'
  }

  @Test
  void test_defaultUrl_specified() {
    WebDsl dsl = new WebPageDslBuilder()
        .defaultUrl('http://localhost:2121')
        .defaultContents('<h1>hello</h1> <a id="big" href="general/bigerror">Big Error</a>')
        .setResponseFor('http://localhost:2121/general/bigerror', '<h1>big error</h1>', 'text/html')
        .setResponseFor('general/littleerror', '<h1>little error</h1>', 'text/html')
        .build()

    dsl.$('#big').click()
    assert dsl.$('h1').text == 'big error'
  }

  @Test
  void test_setResponseFor_absolute_url() {
    WebDsl dsl = new WebPageDslBuilder()
        .defaultContents('<h1>hello</h1> <a id="big" href="general/bigerror">Big Error</a>')
        .setResponseFor('http://localhost/general/bigerror', '<h1>big error</h1>', 'text/html')
        .build()

    dsl.$('#big').click()
    assert dsl.$('h1').text == 'big error'
  }

  @Test
  void test_setResponseFor_relative_url() {
    WebDsl dsl = new WebPageDslBuilder()
        .defaultContents('<h1>hello</h1> <a id="big" href="general/bigerror">Big Error</a>')
        .setResponseFor('general/bigerror', '<h1>big error</h1>', 'text/html')
        .build()

    dsl.$('#big').click()
    assert dsl.$('h1').text == 'big error'
  }

  @Test
  void test_setResponseFor_relative_url_down_path() {
    WebDsl dsl = new WebPageDslBuilder()
        .defaultUrl('http://localhost/down/really/low')
        .defaultContents('<h1>hello</h1> <a id="big" href="general/bigerror">Big Error</a>')
        .setResponseFor('http://localhost/down/really/general/bigerror', '<h1>big error</h1>', 'text/html')
        .build()

    dsl.$('#big').click()
    assert dsl.$('h1').text == 'big error'
  }

  @Test
  void test_setResponseFor_context_relative_url() {
    WebDsl dsl = new WebPageDslBuilder()
        .defaultUrl('http://localhost/down/really/low')
        .defaultContents('<h1>hello</h1> <a id="big" href="/general/bigerror">Big Error</a>')
        .setResponseFor('/general/bigerror', '<h1>big error</h1>', 'text/html')
        .build()

    dsl.$('#big').click()
    assert dsl.$('h1').text == 'big error'
  }

  @Test
  void test_not_using_default_page_contents_or_base_url() {
    WebDsl dsl = new WebPageDslBuilder()
        .setResponseFor('http://thehost/general/bigerror', '<h1>big error</h1>', 'text/html')
        .build('http://thehost/general/bigerror')

    assert dsl.$('h1').text == 'big error'
  }
}