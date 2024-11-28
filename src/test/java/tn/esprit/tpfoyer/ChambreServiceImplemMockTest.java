package tn.esprit.tpfoyer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ChambreServiceImplemMockTest {
    @Mock
    ChambreRepository chambreRepository;
    @InjectMocks
    ChambreServiceImpl chambreService;

    Chambre chambre=new Chambre(1,1200, TypeChambre.SIMPLE, null, null);
    List<Chambre> listChambres = new ArrayList<Chambre>() {
        {
            add(new Chambre(2,4000, TypeChambre.DOUBLE, null, null));
            add(new Chambre(3,4100, TypeChambre.DOUBLE, null, null));
        }
    };

    @Test
    public void testRetrieveChambre() {
        Mockito.when(chambreRepository.findById(Mockito.anyLong())).
                thenReturn(Optional.of(chambre));
        Chambre c = chambreService.retrieveChambre(2L);
        Assertions.assertNotNull(c);
    }
    @Test
    public void testRetrieveAllChambres() {
        Mockito.when(chambreRepository.findAll()).thenReturn(listChambres);
        List<Chambre> listC = chambreService.retrieveAllChambres();
        Assertions.assertEquals(2, listC.size());
    }
    @Test
    public void testAddChambre() {
        Chambre newChambre = new Chambre(4, 5000, TypeChambre.SIMPLE, null, null);
        Mockito.when(chambreRepository.save(Mockito.any(Chambre.class)))
                .thenReturn(newChambre);

        Chambre addedChambre = chambreService.addChambre(newChambre);
        Assertions.assertNotNull(addedChambre);
        Assertions.assertEquals(5000, addedChambre.getNumeroChambre());
    }

    @Test
    public void testUpdateChambre() {
        Chambre updatedChambre = new Chambre(1, 1500, TypeChambre.SIMPLE, null, null);

        // This line is only needed if modifyChambre calls findById
        Mockito.when(chambreRepository.save(Mockito.any(Chambre.class))).thenReturn(updatedChambre);

        Chambre result = chambreService.modifyChambre(updatedChambre);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1500, result.getNumeroChambre());
    }



}