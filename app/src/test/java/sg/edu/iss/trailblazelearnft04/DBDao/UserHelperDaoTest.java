package sg.edu.iss.trailblazelearnft04.DBDao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;


import sg.edu.iss.trailblazelearnft04.Model.User;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


import static org.mockito.Mockito.when;

/**
 * Created by Neelam on 3/22/2018.
 */
@RunWith(PowerMockRunner.class)
public class UserHelperDaoTest {



    FirebaseAuth mockedAuth;
    private DatabaseReference mockedDatabaseReference;
    @Mock
    DataSnapshot dataSnapshot;
    @Mock
    ValueEventListener valueEventListener;
    @Mock
    User expectedUser;


    @Before
    public void setUp() throws Exception {

        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);

       when(dataSnapshot.getValue(User.class)).thenReturn(expectedUser);

    }

    @Test
    public void setUserNameByUserId() throws Exception {

        User resutlUser;
        valueEventListener.onDataChange(dataSnapshot);
        {
            resutlUser = dataSnapshot.getValue(User.class);
        }
        assertNotNull(resutlUser);
        assertThat(resutlUser, is(expectedUser));
    }


}