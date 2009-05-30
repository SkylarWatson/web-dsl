package webdsl.support


class CheckBoxDsl extends ElementDsl {
  CheckBoxDsl(pageContainer, element) {
    super(pageContainer, element)
  }

  def getChecked() {
    element.isDefaultChecked()
  }

  def tableValue(attributeName) {
    checked
  }
}