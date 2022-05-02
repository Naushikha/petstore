package com.example.petstore;

import java.util.ArrayList;
import java.util.List;

public class PetTypeDB {
    private List<PetType> petTypes = new ArrayList<PetType>();
    // Use a singleton
    private static PetTypeDB instance = new PetTypeDB();
    public static PetTypeDB getInstance() {
        return instance;
    }

    public PetType add(PetType newPetType) {
        Integer genID = petTypes.size() + 1;
        newPetType.setPetTypeId(genID);
        petTypes.add(newPetType);
        return newPetType;
    }

    public List<PetType> getAll() {
        return petTypes;
    }

    public PetType getByID(Integer id) {
        for (PetType petType : petTypes) {
            if (petType.getPetTypeId().equals(id)) {
                return petType;
            }
        }
        return null;
    }

    public boolean existsByID(Integer id) {
        for (PetType petType : petTypes) {
            if (petType.getPetTypeId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public PetType updateByID(PetType updatedPetType, Integer petTypeId) {
        for (PetType petType : petTypes) {
            if (petType.getPetTypeId().equals(petTypeId)) {
                if(updatedPetType.getPetTypeName() != null){
                    petType.setPetTypeName(updatedPetType.getPetTypeName());
                }
                return petType;
            }
        }
        return null;
    }

    public boolean deleteByID(Integer id) {
        for (PetType petType : petTypes) {
            if (petType.getPetTypeId().equals(id)) {
                petTypes.remove(petType);
                return true;
            }
        }
        return false;
    }
}
