/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package kan.wheel.widget;

import java.util.List;

/**
 * The simple Array wheel adapter
 */
public class StringWheelAdapter implements WheelAdapter {
	
	/** The default items length */
	public static final int DEFAULT_LENGTH = -1;
	
	// items
	private List<String> list;
	

	/**
	 * Constructor
	 * @param list the items
	 */
	public StringWheelAdapter(List<String > list) {
		this.list = list;
	}
	

	@Override
	public String getItem(int index) {
		if (index >= 0 && index < list.size()) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return list.size();
	}

	@Override
	public int getMaximumLength() {
		return list.size();
	}

}
