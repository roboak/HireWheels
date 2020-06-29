package com.upgrad.hirewheels;

import com.upgrad.hirewheels.dao.*;
import com.upgrad.hirewheels.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class HireWheelsApplication implements CommandLineRunner {
	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ActivityRepository activityRepository;

	@Autowired
	VehicleCategoryRepo vehicleCategoryRepo;

	@Autowired
	VehicleSubCategoryRepository vehicleSubCategoryRepository;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	FuelTypeRepository fuelTypeRepository;

	@Autowired
	RequestStatusRepository requestStatusRepository;

	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	AdminRequestRepository adminRequestRepository;

	@Autowired
	BookingRepository bookingRepository;

	public static void main(String[] args) {
		SpringApplication.run(HireWheelsApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		UserRole adminUserRole = new UserRole();
		adminUserRole.setRoleId(1);
		adminUserRole.setRoleName("Admin");
		UserRole userRole = new UserRole();
		userRole.setRoleId(2);
		userRole.setRoleName("User");
		userRoleRepository.save(adminUserRole);
		userRoleRepository.save(userRole);
		Users adminUser = new Users();
		adminUser.setFirstName("Upgrad");
		adminUser.setLastName("Admim");
		adminUser.setEmail("upgrad@gmail.com");
		adminUser.setMobileNo(9898989890l);
		adminUser.setPassword("admin@123");
		adminUser.setWalletMoney(10000);
		adminUser.setUserRole(userRoleRepository.findByRoleId(1));
		userRepository.save(adminUser);
		List<Activity> listOfActivity = new ArrayList<>();
		Activity activity1 = new Activity();
		activity1.setActivityId(201);
		activity1.setActivityType("BOOKING");
		listOfActivity.add(activity1);
		Activity activity2 = new Activity();
		activity2.setActivityId(202);
		activity2.setActivityType("VEHICLE_REGISTER");
		listOfActivity.add(activity2);
		Activity activity3 = new Activity();
		activity3.setActivityId(203);
		activity3.setActivityType("CAR_OPT_IN");
		listOfActivity.add(activity3);
		Activity activity4 = new Activity();
		activity4.setActivityId(204);
		activity4.setActivityType("CAR_OPT_OUT");
		listOfActivity.add(activity4);
		activityRepository.saveAll(listOfActivity);
		List<VehicleCategory> vehicleCategoryList = new ArrayList<>();
		VehicleCategory vehicleCategory1 = new VehicleCategory();
		vehicleCategory1.setVehicleCategoryId(10);
		vehicleCategory1.setVehicleCategoryName("CAR");
		vehicleCategoryList.add(vehicleCategory1);
		VehicleCategory vehicleCategory2 = new VehicleCategory();
		vehicleCategory2.setVehicleCategoryId(11);
		vehicleCategory2.setVehicleCategoryName("BIKE");
		vehicleCategoryList.add(vehicleCategory2);
		vehicleCategoryRepo.saveAll(vehicleCategoryList);
		List<VehicleSubCategory> vehicleSubCategories = new ArrayList<>();
		VehicleSubCategory vehicleSubCategory1 = new VehicleSubCategory();
		vehicleSubCategory1.setVehicleSubCategoryId(10001);
		vehicleSubCategory1.setVehicleSubCategoryName("SUV");
		vehicleSubCategory1.setPricePerHour(300);
		vehicleSubCategory1.setVehicleCategory(vehicleCategoryRepo.findByVehicleCategoryId(10));
		vehicleSubCategories.add(vehicleSubCategory1);
		VehicleSubCategory vehicleSubCategory2 = new VehicleSubCategory();
		vehicleSubCategory2.setVehicleSubCategoryId(10002);
		vehicleSubCategory2.setVehicleSubCategoryName("SEDAN");
		vehicleSubCategory2.setPricePerHour(350);
		vehicleSubCategory2.setVehicleCategory(vehicleCategoryRepo.findByVehicleCategoryId(10));
		vehicleSubCategories.add(vehicleSubCategory2);
		VehicleSubCategory vehicleSubCategory3 = new VehicleSubCategory();
		vehicleSubCategory3.setVehicleSubCategoryId(10003);
		vehicleSubCategory3.setVehicleSubCategoryName("HATCHBACK");
		vehicleSubCategory3.setPricePerHour(250);
		vehicleSubCategory3.setVehicleCategory(vehicleCategoryRepo.findByVehicleCategoryId(10));
		vehicleSubCategories.add(vehicleSubCategory3);
		VehicleSubCategory vehicleSubCategory4 = new VehicleSubCategory();
		vehicleSubCategory4.setVehicleSubCategoryId(10004);
		vehicleSubCategory4.setVehicleSubCategoryName("CRUISER");
		vehicleSubCategory4.setPricePerHour(200);
		vehicleSubCategory4.setVehicleCategory(vehicleCategoryRepo.findByVehicleCategoryId(11));
		vehicleSubCategories.add(vehicleSubCategory4);
		VehicleSubCategory vehicleSubCategory5 = new VehicleSubCategory();
		vehicleSubCategory5.setVehicleSubCategoryId(10005);
		vehicleSubCategory5.setVehicleSubCategoryName("DIRT BIKE");
		vehicleSubCategory5.setPricePerHour(150);
		vehicleSubCategory5.setVehicleCategory(vehicleCategoryRepo.findByVehicleCategoryId(11));
		vehicleSubCategories.add(vehicleSubCategory5);
		VehicleSubCategory vehicleSubCategory6 = new VehicleSubCategory();
		vehicleSubCategory6.setVehicleSubCategoryId(10006);
		vehicleSubCategory6.setVehicleSubCategoryName("SPORTS BIKE");
		vehicleSubCategory6.setPricePerHour(150);
		vehicleSubCategory6.setVehicleCategory(vehicleCategoryRepo.findByVehicleCategoryId(11));
		vehicleSubCategories.add(vehicleSubCategory6);
		vehicleSubCategoryRepository.saveAll(vehicleSubCategories);
		City city = new City();
		city.setCityId(1);
		city.setCityName("Mumbai");
		cityRepository.save(city);
		List<FuelType> fuelTypeList = new ArrayList<>();
		FuelType fuelType1 = new FuelType();
		fuelType1.setFuelType("Petrol");
		fuelType1.setFuelTypeId(1);
		fuelTypeList.add(fuelType1);
		FuelType fuelType2 = new FuelType();
		fuelType2.setFuelType("Diesel");
		fuelType2.setFuelTypeId(2);
		fuelTypeList.add(fuelType2);
		fuelTypeRepository.saveAll(fuelTypeList);
		List<RequestStatus> requestStatusList = new ArrayList<>();
		RequestStatus requestStatus1 = new RequestStatus();
		requestStatus1.setRequestStatusId(301);
		requestStatus1.setRequestStatusName("PENDING");
		requestStatusList.add(requestStatus1);
		RequestStatus requestStatus2 = new RequestStatus();
		requestStatus2.setRequestStatusId(302);
		requestStatus2.setRequestStatusName("APPROVED");
		requestStatusList.add(requestStatus2);
		RequestStatus requestStatus3 = new RequestStatus();
		requestStatus3.setRequestStatusId(303);
		requestStatus3.setRequestStatusName("REJECTED");
		requestStatusList.add(requestStatus3);
		requestStatusRepository.saveAll(requestStatusList);
		Location location = new Location();
		location.setLocationName("Worli");
		location.setAddress("Dr E Moses Rd, Worli Naka, Upper Worli");
		location.setPincode(400018);
		location.setCity(cityRepository.findById(1).get());
		locationRepository.save(location);
		Vehicle vehicle1 = new Vehicle();
		vehicle1.setUser(userRepository.findById(1).get());
		vehicle1.setVehicleModel("Hyundai Creta");
		vehicle1.setFuelType(fuelTypeRepository.findById(1).get());
		vehicle1.setLocationWithVehicle(locationRepository.findById(1).get());
		vehicle1.setCarImageUrl("https://images.carandbike.com/car-images/large/hyundai/creta/hyundai-creta.webp?v=56");
		vehicle1.setVehicleNumber("KA01LR0987");
		vehicle1.setColor("White");
		vehicle1.setVehicleSubCategory(vehicleSubCategoryRepository.findById(1).get());
		vehicleRepository.save(vehicle1);
		AdminRequest adminRequest = new AdminRequest();
		adminRequest.setActivity(activityRepository.findById(202).get());
		adminRequest.setUserComments("");
		adminRequest.setAdminComments("Approved as added by Admin");
		adminRequest.setUser(userRepository.findById(1).get());
		adminRequest.setRequestStatus(requestStatusRepository.findById(302).get());
		adminRequest.setVehicle(vehicleRepository.findById(1).get());
		adminRequestRepository.save(adminRequest);
//		Booking booking = new Booking();
//		String date="20/06/2020";
//		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//		booking.setAmount(1000);
//		booking.setBookingDate(date1);
//		booking.setPickUpDate(date1);
//		booking.setDropOffDate(date1);
//		booking.setBookingWithUser(userRepository.findById(1).get());
//		booking.setLocationWithBooking(locationRepository.findById(1).get());
//		booking.setVehicleWithBooking(vehicleRepository.findById(1).get());
//		bookingRepository.save(booking);
	}

}
