package sg.edu.iss.trailblazelearnft04.DBDao;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
import java.util.HashMap;

import sg.edu.iss.trailblazelearnft04.Model.Station;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Neelam on 3/23/2018.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
public class StationHelperDaoTest {

    @Mock
    private DatabaseReference mDatabase;
    @Mock
    DataSnapshot dataSnapshot;
    @Mock
    ValueEventListener valueEventListener;
    @Mock
    Station expectedStation;
    @Mock
    FirebaseDatabase mockedFirebaseDatabase;
    @Mock
    StationHelperDao stationHelperDao;
    @Mock
    ChildEventListener childEventListener;

    ArrayList<Station> stationList = new ArrayList<>();
    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mDatabase);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

        when(dataSnapshot.getValue(Station.class)).thenReturn(expectedStation);

    }
    @Test
    public void getStationListForTrainer() throws Exception {

        Station resultStation;
        valueEventListener.onDataChange(dataSnapshot);
        {
            resultStation = dataSnapshot.getValue(Station.class);
        }

        stationList.add(resultStation);
        assertNotNull(resultStation);
        assertThat(resultStation, is(expectedStation));
        assertNotNull(stationList);

    }

    @Test
    public void addNewStation() throws Exception {
        doAnswer(new Answer<Void>(){
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
                valueEventListener.onDataChange(mockedDataSnapshot);

                return null;
            }
        }).when(mDatabase).addValueEventListener(valueEventListener);

        stationHelperDao.addNewStation(1,"dummy","dummy",new HashMap<String, Double>(),"dummy","dummy");

    }

    @Test
    public void updateStation() throws Exception {

        doAnswer(new Answer<Void>(){
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                ChildEventListener childEventListener = (ChildEventListener) invocation.getArguments()[0];

                DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
                childEventListener.onChildChanged(mockedDataSnapshot,anyString());

                return null;
            }
        }).when(mDatabase).addChildEventListener(childEventListener);

        stationHelperDao.updateStation(1,"dummy","dummy","dummy",new HashMap<String, Double>(),"dummy","dummy");

    }


}