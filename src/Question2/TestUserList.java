package Question2;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TestUserList {

    private UserList.User setNewUser() {
      UserList.User temp = new UserList().new User();
      temp.fullName = "abc";
      temp.age = 10;
      return temp;
   }

   private UserList setUserList() {
       String filename = "src/Question2/UserList.ser";
       UserList userList = Main.deserialize(filename);
      if (userList == null)
         userList = new UserList();
      return userList;
   }

   @Test
   public void testGetAge() {
      UserList.User user = setNewUser();
      assertEquals(user.getAge(), 10);
   }

   @Test
   public void testGetFullName() {
      UserList.User user = setNewUser();
      assertEquals(user.fullName, "abc");
   }

   @Test
   public void testFullNameBlank() {
      UserList userList = setUserList();
      for (UserList.User i : userList.list) {
         assertNotEquals(i.fullName.trim(), "");
      }
   }

   @Test
   public void testAgeGreaterThanZero() {
      UserList userList = setUserList();
      for (UserList.User i : userList.list) {
         assertTrue(i.age >= 1);
      }
   }

   @Test
   public void testCourseSize() {
      UserList userList = setUserList();
      for (UserList.User i : userList.list) {
         assertTrue(4 <= i.courses.length && i.courses.length <= 6);
      }
   }

   @Test
   public void testSortedByFullName() {
      UserList userList = setUserList();
      for(int i=0; i < userList.list.size() - 1; i++) {
         UserList.User first = userList.list.get(i);
         UserList.User second = userList.list.get(i);
         assertNotEquals(first.fullName.compareTo(second.fullName), -1);
      }
   }
}