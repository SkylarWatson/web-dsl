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
package webdsl.support.css.selector

import webdsl.support.matchers.AlwaysMatcher
import webdsl.support.matchers.ContainsMatcher
import webdsl.support.matchers.EndsWithMatcher
import webdsl.support.matchers.EqualsMatcher
import webdsl.support.matchers.ListContainsMatcher
import webdsl.support.matchers.StartsWithHyphenatedMatcher
import webdsl.support.matchers.StartsWithMatcher

import java.util.regex.Matcher

class ElementCssSelectorParser {
  List<CssSelector> parse(String selector) {
    String regex = /([^.#\s\[]+)?#?([^.\s\[]+)?[.]?([^\s\[]*)(?:\[(.+?)(?:([*^$~|])?="(.+)")?\])?\s*/

    List<ElementCssSelector> result = []

    Matcher m = selector.replaceAll(/'/, /"/) =~ regex
    while (m.find()) {
      String id = m.group(2)
      String tagName = m.group(1)
      String cssClass = m.group(3)
      String attributeName = m.group(4)
      String matchType = m.group(5)
      String attributeValue = m.group(6)

      if (id || tagName || cssClass || attributeName) {
        Map<String, String> attributes = [:]

        if (cssClass)
          attributes.class = LIST_CONTAINS(cssClass)

        def matcher
        if (attributeName) {
          switch (matchType) {
            case '^':
              matcher = STARTS_WITH(attributeValue)
              break
            case '$':
              matcher = ENDS_WITH(attributeValue)
              break
            case '*':
              matcher = CONTAINS(attributeValue)
              break
            case '~':
              matcher = LIST_CONTAINS(attributeValue)
              break
            case '|':
              matcher = STARTS_WITH_HYPHENATED(attributeValue)
              break
            default:
              matcher = attributeValue ? EQ(attributeValue) : ALWAYS()

          }
          attributes[attributeName] = matcher
        }

        result << new ElementCssSelector(id, tagName, attributes)
      }
    }

    result
  }

  static EQ(String value) {
    new EqualsMatcher(value)
  }

  static ALWAYS() {
    new AlwaysMatcher()
  }

  static STARTS_WITH(String value) {
    new StartsWithMatcher(value)
  }

  static ENDS_WITH(String value) {
    new EndsWithMatcher(value)
  }

  static CONTAINS(String value) {
    new ContainsMatcher(value)
  }

  static LIST_CONTAINS(String value) {
    new ListContainsMatcher(value)
  }

  static STARTS_WITH_HYPHENATED(String value) {
    new StartsWithHyphenatedMatcher(value)
  }
}