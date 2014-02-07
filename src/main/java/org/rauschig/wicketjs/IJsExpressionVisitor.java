package org.rauschig.wicketjs;

import java.io.Serializable;

/**
 * A visitor of the IJsExpression hierarchy.
 */
public interface IJsExpressionVisitor extends Serializable {

    void visit(JsLiteral.JsNumber visitable);

    void visit(JsLiteral.JsBoolean visitable);

    void visit(JsLiteral.JsString visitable);

    void visit(JsLiteral.ObjectLiteral visitable);

    void visit(JsIdentifier visitable);

    void visit(JsExpression visitable);

    void visit(JsCall visitable);

    void visit(JsFunction visitable);

    void visit(JsNamedFunction visitable);

    void visit(JsExpressionList visitable);
}
