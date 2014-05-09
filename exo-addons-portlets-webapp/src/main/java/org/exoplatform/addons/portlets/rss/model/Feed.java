/*
 * Copyright (C) 2003-2014 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.addons.portlets.rss.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: benjamin
 * Date: 07/06/12
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class Feed {
  String title;
  String more;

  List<Item> items;

  public Feed() {
    items = new ArrayList<Item>();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMore() {
    return more;
  }

  public void setMore(String more) {
    this.more = more;
  }

  public List<Item> getItems() {
    return items;
  }

  public void addItem(Item item) {
    this.items.add(item);
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}
