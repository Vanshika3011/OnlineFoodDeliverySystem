package enums;

public enum UserRoles {
    ADMIN(1, "Admin"),
    RESTAURANTADMIN(2, "RestaurantAdmin"),
    CUSTOMER(3, "Customer"),
    DELIVERYAGENT(4, "DeliveryAgent");

    private final int roleIdValue;
    private final String roleName;

    private UserRoles(int roleIdValue, String roleName) {
        this.roleIdValue = roleIdValue;
        this.roleName = roleName;
    }

    public int getRoleIdValue() {
        return roleIdValue;
    }

    public String getRoleName() {
        return roleName;
    }

    public static void getUserRole() {
        UserRoles[] roles = UserRoles.values();
        for (UserRoles role : roles) {
            if(role.getRoleIdValue() > 1) {
                System.out.println("Enter " + role.getRoleIdValue() + " to signup as " + role.getRoleName() + ".");
            }
        }
    }
}
