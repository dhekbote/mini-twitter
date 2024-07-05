package minitwitter.visitor;

import minitwitter.model.User;
import minitwitter.model.UserGroup;

//Visitor pattern for analysis features
public interface UserGroupVisitor {
 void visitUserGroup(UserGroup userGroup);
 void visitUser(User user);
}