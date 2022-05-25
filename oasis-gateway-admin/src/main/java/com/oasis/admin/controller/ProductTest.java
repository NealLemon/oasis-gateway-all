package com.oasis.admin.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName ProductTest
 * @Description TODO
 * @Author zhushaobin
 * @Date 2022/5/19 21:23
 */
public class ProductTest {

    @NotNull
    private long id;
    @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;
    @Size(min = 1)
    private List<String> tags;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
