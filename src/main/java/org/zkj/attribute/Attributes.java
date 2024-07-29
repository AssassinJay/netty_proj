package org.zkj.attribute;

import io.netty.util.AttributeKey;
import org.zkj.session.Session;

public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
