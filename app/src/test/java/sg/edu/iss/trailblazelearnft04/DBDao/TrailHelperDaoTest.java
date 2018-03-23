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

import sg.edu.iss.trailblazelearnft04.Model.Trail;

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
    TrailHelperDao trailHelperDao;
    ArrayList<Trail> trailList = new ArrayList<>();



    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mDatabase);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

        when(dataSnapshot.getValue(Trail.class)).thenReturn(expectedTrail);

    }
    @Test
    public void getTrailsForTrainer()throws  Exception
    {
        Trail trailResult;
        valueEventListener.onDataChange(dataSnapshot);
        {
            trailResult = dataSnapshot.getValue(Trail.class);
        }

        trailList.add(trailResult);
        assertNotNull(trailResult);
        assertThat(trailResult, is(expectedTrail));
        assertNotNull(trailList);
    }

    @Test
    public void updateTrailTest()
    {
        doAnswer(new Answer<Void>(){
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];
                Query query =  Mockito.mock(Query.class);
                DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
                valueEventListener.onDataChange(mockedDataSnapshot);

                return null;
            }
        }).when(mDatabase).addValueEventListener(valueEventListener);

        trailHelperDao.updateTrail(anyString(),anyString(),anyString(),anyString(),anyString(),anyString());

    }
        @Test
        public void deleteDataTest()
        {
            doAnswer(new Answer<Void>(){
                @Override
                public Void answer(InvocationOnMock invocation) throws Throwable {

                    ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];
                    Query query =  Mockito.mock(Query.class);
                    DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
                    valueEventListener.onDataChange(mockedDataSnapshot);

                    return null;
                }
            }).when(mDatabase).addValueEventListener(valueEventListener);

            trailHelperDao.deleteTrail(expectedTrail,"dummy");
        }

}