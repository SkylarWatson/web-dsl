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

import java.util.regex.Matcher


class CssSelectorParser {
  private ElementCssSelectorParser elementSelectorParser = new ElementCssSelectorParser()

  CssSelector parse(String selectorString) {
    Matcher matcher = selectorString =~ /([^,]+)[,]?/

    List<CssSelector> selectors = matcher.collect { parseOrFragment(it[1]) }
    selectors.size() == 1 ? selectors[0] : new OrCssSelector(selectors)
  }

  private CssSelector parseOrFragment(String selectorString) {
    Matcher matcher = selectorString =~ /([^+>]+)([+>]?)/

    CssSelector selector = new InsideCssSelector(elementSelectorParser.parse(matcher[0][1]))
    String operator = matcher[0][2]
    for (int i = 1; i < matcher.size(); ++i) {
      switch (operator) {
        case '>':
          selector = new ParentCssSelector(selector, createChildCssSelector(matcher[i][1]))
          break
        case '+':
          selector = new StalkerCssSelector(selector, createChildCssSelector(matcher[i][1]))
          break
      }
      operator = matcher[i][2]
    }

    selector
  }

  protected ChildCssSelector createChildCssSelector(childSelectionPattern) {
    List<CssSelector> cssSelectors = elementSelectorParser.parse(childSelectionPattern)
    CssSelector exactMatchSelector = cssSelectors.head()
    InsideCssSelector insideCssSelector = cssSelectors.size() > 1 ? new InsideCssSelector(cssSelectors.tail()) : null
    new ChildCssSelector(exactMatchSelector, insideCssSelector)
  }
}