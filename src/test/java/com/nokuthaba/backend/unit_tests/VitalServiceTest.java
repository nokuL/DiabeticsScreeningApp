package com.nokuthaba.backend.unit_tests;

import com.nokuthaba.backend.dto.VitalDto;
import com.nokuthaba.backend.models.*;
import com.nokuthaba.backend.repository.BodyMassIndexRepository;
import com.nokuthaba.backend.repository.VitalsRepository;
import com.nokuthaba.backend.service.DecisionSupportService;
import com.nokuthaba.backend.service.VitalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.Silent.class)
public class VitalServiceTest {

    @Mock
    private VitalsRepository vitalsRepository;

    @Mock
    private DecisionSupportService decisionSupportService;

    @InjectMocks
    private VitalService vitalService;


    @Test
    public void testUpdateVitalSign() {
        VitalSign vitalSign = new VitalSign();
        vitalSign.setId(UUID.randomUUID().toString());
        when(vitalsRepository.existsById(vitalSign.getId())).thenReturn(true);
        when(vitalsRepository.save(vitalSign)).thenReturn(vitalSign);

        VitalSign updatedVitalSign = vitalService.updateVitalSign(vitalSign);

        assertNotNull(updatedVitalSign);
        assertEquals(vitalSign.getId(), updatedVitalSign.getId());
        verify(vitalsRepository, times(1)).existsById(vitalSign.getId());
        verify(vitalsRepository, times(1)).save(vitalSign);
    }

    @Test
    public void testDeleteById() {
        String id = UUID.randomUUID().toString();
        when(vitalsRepository.existsById(id)).thenReturn(true);

        vitalService.deleteById(id);

        verify(vitalsRepository, times(1)).existsById(id);
        verify(vitalsRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteByIdInvalidId() {
        String id = UUID.randomUUID().toString();
        when(vitalsRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> vitalService.deleteById(id));
        verify(vitalsRepository, times(1)).existsById(id);
        verify(vitalsRepository, never()).deleteById(id);
    }

    @Test
    public void testFetchAllVitalSigns() {
        List<VitalSign> vitalSignList = new ArrayList<>();
        vitalSignList.add(new VitalSign());
        when(vitalsRepository.findAll()).thenReturn(vitalSignList);

        List<VitalSign> fetchedVitalSigns = vitalService.fetchAllVitalSigns();

        assertNotNull(fetchedVitalSigns);
        assertEquals(vitalSignList.size(), fetchedVitalSigns.size());
        verify(vitalsRepository, times(1)).findAll();
    }



}