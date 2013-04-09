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


class CssSelectorTest extends AbstractNonServerTest {

  void test_select_by_tag_name_single_element() {
    html {
      p('a')
      div('b')
    }

    webdsl {
      assert $('p').text == 'a'
    }
  }

  void test_select_by_tag_name_multiple_elements() {
    html {
      div('hello')
      p('xxx')
      div(class: "whocares", 'world')
    }

    webdsl {
      assert $('div')*.text == ['hello', 'world']
    }
  }

  void test_select_by_tag_name_ignores_class() {
    html {
      p(class: 'main', 'a')
      div('b')
    }

    webdsl {
      assert $('p').text == 'a'
    }
  }

  void test_select_by_tag_name_not_found() {
    html {
      div('hello')
      div('world')
    }

    webdsl {
      assert $('p') == null
    }
  }

  void test_select_by_class_single_element() {
    html {
      p('a')
      p('b')
      p('c')
      div(class: 'selected', 'd')
    }

    webdsl {
      assert $('.selected').text == 'd'
    }
  }

  void test_select_by_class_multiple_elements() {
    html {
      p('a')
      p(class: 'selected', 'b')
      p('c')
      div(class: 'selected', 'd')
    }

    webdsl {
      assert $('.selected')*.text == ['b', 'd']
    }
  }

  void test_select_by_class_not_found() {
    html {
      p('a')
      p(class: 'other', 'b')
      p('c')
      div(class: 'other2', 'd')
    }

    webdsl {
      assert $('.selected') == null
    }
  }

  void test_select_by_tagName_and_class_single_element() {
    html {
      p('a')
      p(class: 'selected', 'b')
      p('c')
      div(class: 'selected', 'd')
    }

    webdsl {
      assert $('p.selected').text == 'b'
    }
  }

  void test_select_by_tagName_and_class_multiple_elements() {
    html {
      p('a')
      p(class: 'selected', 'b')
      p('c')
      div(class: 'selected', 'd')
      p(class: 'selected', 'e')
    }

    webdsl {
      assert $('p.selected')*.text == ['b', 'e']
    }
  }

  void test_select_by_tagName_and_class_not_found() {
    html {
      p('a')
      p('b')
      p('c')
      div(class: 'selected', 'd')
    }

    webdsl {
      assert $('p.selected') == null
    }
  }

  void test_select_by_id_single_element() {
    html {
      p('a')
      p('b')
      p(id: 'myP', 'c')
    }

    webdsl {
      assert $('#myP').text == 'c'
    }
  }

  void test_select_by_id_multiple_elements() {
    html {
      p(id: 'myP', 'a')
      p('b')
      p(id: 'myP', 'c')
    }

    webdsl {
      assert $('#myP')*.text == ['a', 'c']
    }
  }

  void test_select_by_id_ignores_class() {
    html {
      p('a')
      p('b')
      p(id: 'myP', class: 'selected', 'c')
    }

    webdsl {
      assert $('#myP').text == 'c'
    }
  }

  void test_select_by_id_not_found() {
    html {
      p('a')
      p('b')
      p('c')
    }

    webdsl {
      assert $('#myP') == null
    }
  }

  void test_select_by_id_and_class_single_element() {
    html {
      p('a')
      p(id: 'myP', class: 'main', 'b')
      p(id: 'myP', class: 'main2', 'c')
    }

    webdsl {
      assert $('#myP.main').text == 'b'
    }
  }

  void test_select_by_id_and_class_multiple_elements() {
    html {
      p('a')
      p(id: 'myP', class: 'main', 'b')
      p(id: 'myP', class: 'main', 'c')
      p('d')
    }

    webdsl {
      assert $('#myP.main')*.text == ['b', 'c']
    }
  }

  void test_select_by_id_and_class_not_found() {
    html {
      p('a')
      p(id: 'myP', 'b')
      p('c')
    }

    webdsl {
      assert $('#myP.main') == null
    }
  }

  void test_select_multiple_by_id_then_class() {
    html {
      div('a') {
        div(class: 'owner', '1')
      }
      div(id: 'myP', 'b') {
        div(class: 'owner', '2')
        div(class: 'owner', '3')
        div(class: 'owner', '4')
        div {
          div(class: 'owner', '5')
        }
      }
      div('c') {
        div(class: 'owner', '6')
      }
    }

    webdsl {
      assert $('#myP .owner')*.text == ['2', '3', '4', '5']
    }
  }

}