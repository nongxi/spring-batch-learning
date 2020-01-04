package io.spring.bitch.configuration;

import org.springframework.batch.item.*;

import java.util.Date;
import java.util.List;

public class StatefullItemReader implements ItemStreamReader {

	private final List<String> items;
	private int curIndex = -1;
	private boolean restart = false;
	private String firstName;
	private String lastName;
	private Date birthdate;

/*	public StatefullItemReader(List<String> items,String firstName, String lastName, Date birthdate) {
		this.items = items;
		this.curIndex = 0;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
	}*/

	public StatefullItemReader(List<String> items) {
		this.items = items;
		this.curIndex = 0;
	}

	@Override
	public String read() throws Exception {
		String item = null;

		if(this.curIndex < this.items.size()) {
			item = this.items.get(this.curIndex);
			this.curIndex++;
		}

		if(this.curIndex == 42 && !restart) {
			throw new RuntimeException("The Answer to the Ultimate Question of Life, the Universe, and Everything");
		}

		return item;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if(executionContext.containsKey("curIndex")) {
			this.curIndex = executionContext.getInt("curIndex");
			this.restart = true;
		}
		else {
			this.curIndex = 0;
			executionContext.put("curIndex", this.curIndex);
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.put("curIndex", this.curIndex);
		//executionContext.put("firstName", this.firstName);
		//executionContext.put("lastName", this.lastName);
	}

	@Override
	public void close() throws ItemStreamException {
	}
}
