package minitwitter.model;

import minitwitter.visitor.UserGroupVisitor;

//Composite pattern for user groups
public abstract class UserGroupComponent {
 public void add(UserGroupComponent userGroupComponent) {
     throw new UnsupportedOperationException();
 }
 
 public void remove(UserGroupComponent userGroupComponent) {
     throw new UnsupportedOperationException();
 }
 
 public UserGroupComponent getChild(int i) {
     throw new UnsupportedOperationException();
 }
 
 public String getId() {
     throw new UnsupportedOperationException();
 }
 
 public void accept(UserGroupVisitor visitor) {
     throw new UnsupportedOperationException();
 }
}