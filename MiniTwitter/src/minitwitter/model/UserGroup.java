package minitwitter.model;

import minitwitter.visitor.UserGroupVisitor;

import java.util.ArrayList;
import java.util.List;

public class UserGroup extends UserGroupComponent {
    private String id;
    private List<UserGroupComponent> userGroupComponents = new ArrayList<>();

    public UserGroup(String id) {
        this.id = id;
    }

    @Override
    public void add(UserGroupComponent userGroupComponent) {
        userGroupComponents.add(userGroupComponent);
    }

    @Override
    public void remove(UserGroupComponent userGroupComponent) {
        userGroupComponents.remove(userGroupComponent);
    }

    @Override
    public UserGroupComponent getChild(int i) {
        return userGroupComponents.get(i);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void accept(UserGroupVisitor visitor) {
        visitor.visitUserGroup(this);
        for (UserGroupComponent userGroupComponent : userGroupComponents) {
            userGroupComponent.accept(visitor);
        }
    }
    
    public List<UserGroupComponent> getComponents() {
        return userGroupComponents;
    }
}
