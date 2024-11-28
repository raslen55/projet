package tn.esprit.tpfoyer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BlocServiceImplemMockTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    private Bloc bloc;

    @BeforeEach
    void setUp() {
        bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100);
    }

    @Test
    void testAddBloc() {
        // Arrange
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc savedBloc = blocService.addBloc(bloc);

        // Assert
        assertNotNull(savedBloc);
        assertEquals("Bloc A", savedBloc.getNomBloc());
        assertEquals(100, savedBloc.getCapaciteBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        when(blocRepository.findById(anyLong())).thenReturn(Optional.of(bloc));

        // Act
        Bloc retrievedBloc = blocService.retrieveBloc(1L);

        // Assert
        assertNotNull(retrievedBloc);
        assertEquals("Bloc A", retrievedBloc.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveAllBlocs() {
        // Arrange
        List<Bloc> listBlocs = new ArrayList<>();
        listBlocs.add(new Bloc(1L, "Bloc A", 100, null, null));
        listBlocs.add(new Bloc(2L, "Bloc B", 200, null, null));
        when(blocRepository.findAll()).thenReturn(listBlocs);

        // Act
        List<Bloc> blocs = blocService.retrieveAllBlocs();

        // Assert
        assertEquals(2, blocs.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRemoveBloc() {
        // Arrange
        long blocId = 1L;
        doNothing().when(blocRepository).deleteById(blocId);

        // Act
        blocService.removeBloc(blocId);

        // Assert
        verify(blocRepository, times(1)).deleteById(blocId);
    }

    @Test
    void testTrouverBlocsSansFoyer() {
        // Arrange
        List<Bloc> listBlocs = new ArrayList<>();
        listBlocs.add(new Bloc(1L, "Bloc A", 100, null, null));
        listBlocs.add(new Bloc(2L, "Bloc B", 200, null, null));
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(listBlocs);

        // Act
        List<Bloc> blocsSansFoyer = blocService.trouverBlocsSansFoyer();

        // Assert
        assertEquals(2, blocsSansFoyer.size());
        verify(blocRepository, times(1)).findAllByFoyerIsNull();
    }

    @Test
    void testTrouverBlocsParNomEtCap() {
        // Arrange
        List<Bloc> listBlocs = new ArrayList<>();
        listBlocs.add(new Bloc(1L, "Bloc A", 100, null, null));
        when(blocRepository.findAllByNomBlocAndCapaciteBloc("Bloc A", 100)).thenReturn(listBlocs);

        // Act
        List<Bloc> blocs = blocService.trouverBlocsParNomEtCap("Bloc A", 100);

        // Assert
        assertEquals(1, blocs.size());
        assertEquals("Bloc A", blocs.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAllByNomBlocAndCapaciteBloc("Bloc A", 100);
    }
}
