package com.example.petstore;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/v1/pet-types")
@Produces("application/json")
public class PetTypeResource {

	private PetTypeDB petTypeDB = PetTypeDB.getInstance();

	// Just to create a default set of Pet types in the DB; Not a standard REST API procedure!
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Populate Default Pet Types", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))) })
	@GET
	@Path("populate-defaults")
	public Response populateDefaultPetTypes() {
		PetType petType1 = new PetType();
		petType1.setPetTypeId(1);
		petType1.setPetTypeName("Dog");

		PetType petType2 = new PetType();
		petType2.setPetTypeId(1);
		petType2.setPetTypeName("Cat");

		PetType petType3 = new PetType();
		petType3.setPetTypeId(1);
		petType3.setPetTypeName("Bird");

		petTypeDB.add(petType1);
		petTypeDB.add(petType2);
		petTypeDB.add(petType3);
		return Response.ok(petTypeDB.getAll()).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Added pet type", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
			@APIResponse(responseCode = "404", description = "No Pet type found for the id.") })
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPetType(PetType petType) {
		PetType addedPetType = petTypeDB.add(petType);
		return Response.ok(addedPetType).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pet types", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))) })
	@GET
	public Response getPetTypes() {
		return Response.ok(petTypeDB.getAll()).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet type for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
			@APIResponse(responseCode = "404", description = "No Pet type found for the id.") })
	@GET
	@Path("{petTypeId}")
	public Response getPetType(@PathParam("petTypeId") int petTypeId) {
		if (petTypeId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}

		PetType petType = petTypeDB.getByID(petTypeId);
		if (petType == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(petType).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Updated pet type", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
			@APIResponse(responseCode = "404", description = "No Pet type found for the id.") })
	@PUT
	@Path("update/{petTypeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePetType(PetType toUpdate, @PathParam("petTypeId") Integer id) {

		if(!petTypeDB.existsByID(id)){
			return Response.status(Status.NOT_FOUND).build();
		}

		PetType updatedPetType = petTypeDB.updateByID(toUpdate, id);

		return Response.ok(updatedPetType).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Deleted pet type", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
			@APIResponse(responseCode = "404", description = "No Pet type found for the id.") })
	@DELETE
	@Path("delete/{petTypeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePetType(@PathParam("petTypeId") Integer id) {

		if(!petTypeDB.existsByID(id)){
			return Response.status(Status.NOT_FOUND).build();
		}

		boolean status = petTypeDB.deleteByID(id);

		return Response.ok(status).build();
	}
}
