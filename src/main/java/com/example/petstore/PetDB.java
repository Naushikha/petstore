package com.example.petstore;

import java.util.ArrayList;
import java.util.List;

public class PetDB {
    private List<Pet> pets = new ArrayList<Pet>();
    // Use a singleton
    private static PetDB instance = new PetDB();
    public static PetDB getInstance() {
        return instance;
    }

    public Pet add(Pet newPet) {
        Integer genID = pets.size() + 1;
        newPet.setPetId(genID);
        pets.add(newPet);
        return newPet;
    }

    public List<Pet> getAll() {
        return pets;
    }

    public Pet getByID(Integer id) {
        for (Pet pet : pets) {
            if (pet.getPetId().equals(id)) {
                return pet;
            }
        }
        return null;
    }

    public boolean existsByID(Integer id) {
        for (Pet pet : pets) {
            if (pet.getPetId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Pet updateByID(Pet updatedPet, Integer petId) {
        for (Pet pet : pets) {
            if (pet.getPetId().equals(petId)) {
                if(updatedPet.getPetAge() != null){
                    pet.setPetAge(updatedPet.getPetAge());
                }
                if(updatedPet.getPetName() != null){
                    pet.setPetName(updatedPet.getPetName());
                }
                if(updatedPet.getPetType() != null){
                    pet.setPetType(updatedPet.getPetType());
                }
                return pet;
            }
        }
        return null;
    }

    public boolean deleteByID(Integer id) {
        for (Pet pet : pets) {
            if (pet.getPetId().equals(id)) {
                pets.remove(pet);
                return true;
            }
        }
        return false;
    }

}
