# HireWheels

Tables and constants for the creation of preloaded values of the application.

# UserRole Table
RoleId = 1;
RoleName = Admin;
RoleId = 2;
RoleName = User;

# User Table
FirstName("Upgrad");
LastName("Admim");
Email("upgrad@gmail.com");
MobileNo(9898989890);
Password("admin@123");
WalletMoney(10000);

# Activity
ActivityId(201);
ActivityType("VEHICLE_REGISTER");
ActivityId(202);
ActivityType("CAR_OPT_IN");
ActivityId(203);
ActivityType("CAR_OPT_OUT");

# Vehicle Category
VehicleCategoryId(10);
VehicleCategoryName("CAR");
VehicleCategoryId(11);
VehicleCategoryName("BIKE");

# VehicleSubCategory
VehicleSubCategoryId(10001);
VehicleSubCategoryName("SUV");
PricePerHour(300);
VehicleSubCategoryId(10002);
VehicleSubCategoryName("SEDAN");
PricePerHour(350);
VehicleSubCategoryId(10003);
VehicleSubCategoryName("HATCHBACK");
PricePerHour(250);
VehicleSubCategoryId(10004);
VehicleSubCategoryName("CRUISER");
PricePerHour(200);
VehicleSubCategoryId(10005);
VehicleSubCategoryName("DIRT BIKE");
PricePerHour(150);
VehicleSubCategoryId(10006);
VehicleSubCategoryName("SPORTS BIKE");
PricePerHour(150);

# City
CityId(1);
CityName("Mumbai");

#Fuel Type
FuelType("Petrol");
FuelTypeId(1);
FuelType("Diesel");
FuelTypeId(2);

#RequestStatus
RequestStatusId(301);
RequestStatusName("PENDING");
RequestStatusId(302);
RequestStatusName("APPROVED");
RequestStatusId(303);
RequestStatusName("REJECTED");

#Location
LocationName("Worli");
Address("Dr E Moses Rd, Worli Naka, Upper Worli");
Pincode(400018);
