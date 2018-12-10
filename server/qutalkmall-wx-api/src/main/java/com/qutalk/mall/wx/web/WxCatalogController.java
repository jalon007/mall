package com.qutalk.mall.wx.web;

import com.qutalk.mall.wx.service.HomeCacheManager;
import com.qutalk.mall.wx.view.LitemallCategoryView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.qutalk.mall.core.util.ResponseUtil;
import com.qutalk.mall.db.domain.LitemallCategory;
import com.qutalk.mall.db.service.LitemallCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/catalog")
@Validated
public class WxCatalogController {
    private final Log logger = LogFactory.getLog(WxCatalogController.class);

    @Autowired
    private LitemallCategoryService categoryService;

    /**
     * 分类栏目
     *
     * @param id   分类类目ID
     *             如果分类类目ID是空，则选择第一个分类类目。
     *             需要注意，这里分类类目是一级类目
     * @param page 分页页数
     * @param size 分页大小
     * @return 分类栏目
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * categoryList: xxx,
     * currentCategory: xxx,
     * currentSubCategory: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("index")
    public Object index(Integer id,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size) {

        // 所有一级分类目录
        List<LitemallCategory> l1CatList = categoryService.queryL1();

        // 当前一级分类目录
        LitemallCategory currentCategory = null;
        if (id != null) {
            currentCategory = categoryService.findById(id);
        } else {
            currentCategory = l1CatList.get(0);
        }

        // 当前一级分类目录对应的二级分类目录
        List<LitemallCategory> currentSubCategory = null;
        if (null != currentCategory) {
            currentSubCategory = categoryService.queryByPid(currentCategory.getId());
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("categoryList", l1CatList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResponseUtil.ok(data);
    }

    /**
     * 一次性获取全部分类数据
     *
     * @return
     */
    @GetMapping("all")
    public Object queryAll() {
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.CATALOG)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.CATALOG));
        }


        // 所有一级分类目录
        List<LitemallCategory> l1CatList = categoryService.queryL1();

        //所有子分类列表
        Map<Integer, List<LitemallCategory>> allList = new HashMap<>();
        List<LitemallCategory> sub;
        for (LitemallCategory category : l1CatList) {
            sub = categoryService.queryByPid(category.getId());
            allList.put(category.getId(), sub);
        }

        // 当前一级分类目录
        LitemallCategory currentCategory = l1CatList.get(0);

        // 当前一级分类目录对应的二级分类目录
        List<LitemallCategory> currentSubCategory = null;
        if (null != currentCategory) {
            currentSubCategory = categoryService.queryByPid(currentCategory.getId());
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("categoryList", l1CatList);
        data.put("allList", allList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);

        //缓存数据
        HomeCacheManager.loadData(HomeCacheManager.CATALOG, data);
        return ResponseUtil.ok(data);
    }

    /**
     * pc首页分类导航
     * @return
     */
    @GetMapping("pall")
    public Object categories() {
        List<LitemallCategoryView> categoryViews = new ArrayList<>();
        LitemallCategoryView litemallCategoryView =new LitemallCategoryView();
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.CATALOG_PC)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.CATALOG_PC));
        }
        // 所有一级分类目录
        List<LitemallCategory> cates = categoryService.queryL1();
        for (LitemallCategory category : cates) {
            BeanUtils.copyProperties(category,litemallCategoryView);
            List<LitemallCategory> subCates = categoryService.queryByPid(category.getId());

            List<LitemallCategoryView> subCategoryViews = new ArrayList<>();
            if (subCates!=null){
                for(LitemallCategory subcate:subCates){
                    LitemallCategoryView subCategoryView =new LitemallCategoryView();
                    BeanUtils.copyProperties(subcate,subCategoryView);
                    subCategoryViews.add(subCategoryView);
                }
                litemallCategoryView.setSubCategory(subCategoryViews);
            }
            categoryViews.add(litemallCategoryView);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("categoryList", categoryViews);
        //缓存数据
        HomeCacheManager.loadData(HomeCacheManager.CATALOG_PC, data);
        return ResponseUtil.ok(data);
    }

    /**
     * 当前分类栏目
     *
     * @param id 分类类目ID
     * @return 当前分类栏目
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * currentCategory: xxx,
     * currentSubCategory: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("current")
    public Object current(@NotNull Integer id) {
        // 当前分类
        LitemallCategoryView categoryView=new LitemallCategoryView();
        BeanUtils.copyProperties(categoryService.findById(id),categoryView);

        List<LitemallCategory> currentSubCategory = categoryService.queryByPid(categoryView.getId());
        List<LitemallCategoryView> subCateViews =new ArrayList<>();
        if(currentSubCategory!=null){
            for(LitemallCategory subCate :currentSubCategory){
                LitemallCategoryView subCategoryView = new LitemallCategoryView();
                BeanUtils.copyProperties(subCate,subCategoryView);
                subCateViews.add(subCategoryView);
            }
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("currentCategory", categoryView);
        data.put("currentSubCategory", subCateViews);
        return ResponseUtil.ok(data);
    }
}