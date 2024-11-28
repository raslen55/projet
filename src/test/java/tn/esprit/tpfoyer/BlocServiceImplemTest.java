//package tn.esprit.tpfoyer;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import tn.esprit.tpfoyer.entity.Bloc;
//import tn.esprit.tpfoyer.repository.BlocRepository;
//import tn.esprit.tpfoyer.service.IBlocService;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import java.util.List;
//import java.util.Optional;
//@SpringBootTest
//@TestMethodOrder(OrderAnnotation.class)
//public class BlockServiceImplemTest {
//    @Autowired
//    IBlocService blocService;
//    @Autowired
//    BlocRepository blocRepository;
//    private Bloc bloc;
//    @BeforeEach
//    void setUp() {
//        bloc = new Bloc();
//        bloc.setNomBloc("Bloc A");
//        bloc.setCapaciteBloc(100);
//        blocRepository.save(bloc);  // Save the initial bloc for tests
//    }
//
//    @Test
//    void testAddBloc() {
//        // Arrange
//        Bloc newBloc = new Bloc();
//        newBloc.setNomBloc("Bloc B");
//        newBloc.setCapaciteBloc(200);
//
//        // Act
//        Bloc savedBloc = blocService.addBloc(newBloc);
//
//        // Assert
//        assertNotNull(savedBloc);
//        assertEquals("Bloc B", savedBloc.getNomBloc());
//        assertEquals(200, savedBloc.getCapaciteBloc());
//    }
//
//    @Test
//    void testRetrieveBloc() {
//        // Act
//        Bloc retrievedBloc = blocService.retrieveBloc(bloc.getIdBloc());
//
//        // Assert
//        assertNotNull(retrievedBloc);
//        assertEquals(bloc.getNomBloc(), retrievedBloc.getNomBloc());
//    }
//
//    @Test
//    void testRetrieveAllBlocs() {
//        // Arrange
//        Bloc newBloc = new Bloc();
//        newBloc.setNomBloc("Bloc B");
//        newBloc.setCapaciteBloc(200);
//        blocRepository.save(newBloc);
//
//        // Act
//        List<Bloc> blocs = blocService.retrieveAllBlocs();
//
//        // Assert
//        assertEquals(2, blocs.size());
//    }
//
//    @Test
//    void testRemoveBloc() {
//        // Act
//        blocService.removeBloc(bloc.getIdBloc());
//
//        // Assert
//        Optional<Bloc> deletedBloc = blocRepository.findById(bloc.getIdBloc());
//        assertThat(deletedBloc).isEmpty();
//    }
//
//    @Test
//    void testModifyBloc() {
//        // Arrange
//        bloc.setNomBloc("Bloc Modified");
//
//        // Act
//        Bloc modifiedBloc = blocService.modifyBloc(bloc);
//
//        // Assert
//        assertNotNull(modifiedBloc);
//        assertEquals("Bloc Modified", modifiedBloc.getNomBloc());
//    }
//
//    @Test
//    void testTrouverBlocsSansFoyer() {
//        // Act
//        List<Bloc> blocsSansFoyer = blocService.trouverBlocsSansFoyer();
//
//        // Assert
//        assertEquals(1, blocsSansFoyer.size());
//        assertEquals(bloc.getNomBloc(), blocsSansFoyer.get(0).getNomBloc());
//    }
//
//    @Test
//    void testTrouverBlocsParNomEtCap() {
//        // Act
//        List<Bloc> blocs = blocService.trouverBlocsParNomEtCap("Bloc A", 100);
//
//        // Assert
//        assertEquals(1, blocs.size());
//        assertEquals(bloc.getNomBloc(), blocs.get(0).getNomBloc());
//    }
//}
