import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void initializeRestaurantObject()
    {
    	createRestaurantObject();
    	addMenu();
    }
    
  //>>>>>>>>>>>>>>>>>>>>>>CALCULATING TOTAL ORDER COST<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void total_order_cost_should_be_sum_of_prices_of_selected_items() throws restaurantNotFoundException {
    	List<String> selectedItemArray = new ArrayList<>();
    	selectedItemArray.add("Sweet corn soup");
    	selectedItemArray.add("Vegetable lasagne");
    	
    	double totalOrderCost = restaurant.getTotalOrderValue(selectedItemArray);
    	assertEquals(388, totalOrderCost);
    }
    
    @Test
    public void total_order_cost_should_be_zero_when_no_item_is_selected() throws restaurantNotFoundException {
    	List<String> selectedItemArray = null;
    	double totalOrderCost = restaurant.getTotalOrderValue(selectedItemArray);
    	assertEquals(0, totalOrderCost);
    	
    }
  //>>>>>>>>>>>>>>>>>>>>>>CALCULATING TOTAL ORDER COST<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    
    
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("16:00:00"));
        assertTrue(spiedRestaurant.isRestaurantOpen()); //check evaluaion rubics
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:00:00"));
        assertFalse(spiedRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    private void createRestaurantObject() {
		LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
	}
    
    private void addMenu() {
		restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
	}
}