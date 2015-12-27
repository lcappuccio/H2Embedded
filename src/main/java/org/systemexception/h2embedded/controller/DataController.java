package org.systemexception.h2embedded.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.systemexception.h2embedded.domain.Data;
import org.systemexception.h2embedded.service.DataService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

/**
 * @author leo
 * @date 11/10/15 16:49
 */
@EnableSwagger2
@RestController
@RequestMapping(value = "/api/data")
@Api(basePath = "/api/data", value = "Data", description = "Data REST API")
public class DataController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final DataService dataService;

	@Autowired
	public DataController(DataService dataService) {
		this.dataService = dataService;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType
			.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@ApiOperation(value = "Create data", notes = "Adds data to the database")
	private Data create(@RequestBody @Valid Data data) {
		logger.info("Received CREATE: " + data.getDataValue());
		return dataService.create(data);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiOperation(value = "Delete data", notes = "Deletes data from the database")
	private HttpStatus delete(@PathVariable("id") String id) {
		logger.info("Received DELETE: " + id);
		if (dataService.delete(Integer.valueOf(id))) {
			return HttpStatus.OK;
		} else {
			return HttpStatus.NOT_FOUND;
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiOperation(value = "Find data by id", notes = "Use internal database id")
	private Data findById(@PathVariable("id") String id) {
		logger.info("Received Get: " + id);
		return dataService.findById(Integer.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "List all data", notes = "Produces the full data list in database")
	private List<Data> findAll() {
		logger.info("Received GET all persons");
		return dataService.findAll();
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiOperation(value = "Update data", notes = "Unknown behaviour if id does not exist")
	private HttpStatus update(@RequestBody @Valid Data data) {
		logger.info("Received UPDATE: " + data.getDataId() + ", " + data.getDataValue());
		if (dataService.update(data)) {
			return HttpStatus.OK;
		} else {
			return HttpStatus.NOT_FOUND;
		}
	}
}
