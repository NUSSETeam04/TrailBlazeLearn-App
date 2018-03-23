package sg.edu.iss.trailblazelearnft04.DBDao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.ArrayList;

import sg.edu.iss.trailblazelearnft04.Activity.AddNewTrailActivity;
import sg.edu.iss.trailblazelearnft04.Model.Trail;
import sg.edu.iss.trailblazelearnft04.Model.User;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Created by Neelam on 3/22/2018.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class TrailHelperDaoTest {

    @Mock
    private DatabaseReference mDatabase;
    @Mock
    DataSnapshot dataSnapshot;
    @Mock
    ValueEventListener valueEventListener;
    @Mock
    Trail expectedTrail;
    @Mock
    FirebaseDatabase mockedFirebaseDatabase;


    @Mock
   //@InjectMocks
    TrailHelperDao trailHelperDao;
    ArrayList<Trail> trailList = new ArrayList<>();



    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mDatabase);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        //when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

        when(dataSnapshot.getValue(Trail.class)).thenReturn(expectedTrail);
       // when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
    }
    @Test
    public void getTrailsForTrainer()throws  Exception
    {
        Trail trailResult;
        valueEventListener.onDataChange(dataSnapshot);
        {
            trailResult = dataSnapshot.getValue(Trail.class);
        }
       // new TrailHelperDao().addNewTrail(trailResult.getTrailName(),trailResult.getTrailDate(),trailResult.getTimestamp(),trailResult.getTrailId(),trailResult.getKey(),"aaa");

        trailList.add(trailResult);
//        assertNotNull(trailResult);
        assertThat(trailResult, is(expectedTrail));
        assertNotNull(trailList);
    }

    @Test
    public void updateTrailTest()
    {
        PowerMockito.mockStatic(FirebaseDatabase.class);
        mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        mDatabase = Mockito.mock(DatabaseReference.class);
        //when(FirebaseDatabase.getInstance().getReference()).thenReturn(mockedDatabaseReference);
        DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);


        Trail resultTrail;

        valueEventListener.onDataChange(dataSnapshot);
        {
            resultTrail = dataSnapshot.getValue(Trail.class);

        }

             trailHelperDao.updateTrail("an","aa","aa","aa","aa","aa");



        }


        @Test
        public void testDeleteData()
        {
            /*doAnswer(new Answer<Void>(){
                @Override
                public Void answer(InvocationOnMock invocation) throws Throwable {

                    ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];
                    Query query =  Mockito.mock(Query.class);
                    DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
                    //when(mockedDataSnapshot.getValue(User.class)).thenReturn(testOrMockedUser)

                    valueEventListener.onDataChange(mockedDataSnapshot);
                    //valueEventListener.onCancelled(...);

                    return null;
                }
            }).when(mockedDatabaseReference).addValueEventListener(valueEventListener);

            trailHelperDao.deleteTrail(expectedTrail,"aa");*/
        }

}