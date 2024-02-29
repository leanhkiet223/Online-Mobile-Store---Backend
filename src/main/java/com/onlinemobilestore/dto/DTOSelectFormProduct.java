package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.Category;
import com.onlinemobilestore.entity.Color;
import com.onlinemobilestore.entity.Storage;
import com.onlinemobilestore.entity.Trademark;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class DTOSelectFormProduct {
    List<Category> categories;
    List<Trademark> trademarks;
    List<Color> colors;
    List<Storage> storages;
}
