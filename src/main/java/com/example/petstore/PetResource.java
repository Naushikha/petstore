package com.example.petstore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {

	private PetDB petDB = PetDB.getInstance();

	// Just to create a default set of Pets in the DB; Not a standard REST API procedure!
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Populate Default Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("populate-defaults")
	public Response populateDefaultPets() {
		Pet pet1 = new Pet();
		pet1.setPetId(1);
		pet1.setPetAge(3);
		pet1.setPetName("Boola");
		pet1.setPetType("Dog");

		Pet pet2 = new Pet();
		pet2.setPetId(2);
		pet2.setPetAge(4);
		pet2.setPetName("Sudda");
		pet2.setPetType("Cat");

		Pet pet3 = new Pet();
		pet3.setPetId(3);
		pet3.setPetAge(2);
		pet3.setPetName("Peththappu");
		pet3.setPetType("Bird");

		petDB.add(pet1);
		petDB.add(pet2);
		petDB.add(pet3);
		return Response.ok(petDB.getAll()).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Added pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPet(Pet pet) {
		Pet addedPet = petDB.add(pet);
		return Response.ok(addedPet).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {
		return Response.ok(petDB.getAll()).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Pet pet = petDB.getByID(petId);
		if (pet == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(pet).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Updated pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@PUT
	@Path("update/{petId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePet(Pet toUpdate, @PathParam("petId") Integer id) {

		if(!petDB.existsByID(id)){
			return Response.status(Status.NOT_FOUND).build();
		}

		Pet updatedPet = petDB.updateByID(toUpdate, id);

		return Response.ok(updatedPet).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Deleted pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@DELETE
	@Path("delete/{petId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePet(@PathParam("petId") Integer id) {

		if(!petDB.existsByID(id)){
			return Response.status(Status.NOT_FOUND).build();
		}

		boolean status = petDB.deleteByID(id);

		return Response.ok(status).build();
	}
}
