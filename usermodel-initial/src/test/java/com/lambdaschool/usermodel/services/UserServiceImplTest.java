package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// use the database

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception
    {
        // mocks -> fake data
        // stubs -> fake methods
        // Java -> mocks
        MockitoAnnotations.initMocks(this);

//        List<User> myList = userService.findAll();
//        for (User u : myList)
//        {
//            System.out.println(u.getUserid() + " " + u.getUsername());
//        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findUserById()
    {
        assertEquals("test admin", userService.findUserById(4).getUsername());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void b_findUserByIdNotFound()
    {
        assertEquals("", userService.findUserById(10000).getUsername());
    }

    @Test
    public void c_findByNameContaining()
    {
        assertEquals(1, userService.findByNameContaining("barnbarn").size());
    }

    @Test
    public void d_findAll()
    {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void e_delete()
    {
        userService.delete(4);
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void f_findByName()
    {
        assertEquals("test barnbarn", userService.findByName("test barnbarn").getUsername());
    }

    @Test
    public void g_save()
    {
        // create a user to save
        // admin, data, user
        User u1 = new User("test danny",
                "password",
                "admin@lambdaschool.local");

        Role r1 = new Role("turtle");
        r1.setRoleid(1);

        u1.getRoles()
                .add(new UserRoles(u1, r1));

        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));


        User addUser = userService.save(u1);
        assertNotNull(addUser);
        assertEquals(u1.getUsername(), addUser.getUsername());

    }

    @Test
    public void h_update() {
    }

    @Test
    public void i_deleteAll()
    {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());
    }
}