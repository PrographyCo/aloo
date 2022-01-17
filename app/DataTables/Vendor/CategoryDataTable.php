<?php
namespace App\DataTables\Vendor;

use App\Http\Controllers\Controller;
use App\Models\Category;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class CategoryDataTable extends DataTable
{

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('description_'.Controller::$lang, static function ($query){
                return \WEBHelper::truncate($query->{'description_'.Controller::$lang}, 80);
            })
            ->addColumn('action', static function ($query){
                return '<a href="'. route('vendor.category.show' ,$query) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                    '<a href="'. route('vendor.category.edit' ,$query) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';
            })->rawColumns([
                'action',
                'description_'.Controller::$lang,
            ]);
    }

    public function query(Category $model)
    {
        return $model
            ->newQuery()->where('vendor_id', \Auth::id())->withCount('items');
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('category-table')
            ->columns($this->getColumns())
            ->minifiedAjax()
//            ->dom('Bfrtip')
            ->orderBy(1)
            ->buttons(
                Button::make('create'),
                Button::make('export'),
                Button::make('print'),
                Button::make('reset'),
                Button::make('reload')
            );
    }

    protected function getColumns()
    {
        return [
            Column::make('id'),
            Column::make('name_'.Controller::$lang)->title(__('web/vendor/datatable.category.name')),
            Column::make('description_'.Controller::$lang)->title(__('web/vendor/datatable.category.desecration')),
            Column::make('items_count')->title(__('web/vendor/datatable.category.items_count')),
            Column::make('created_at')->title(__('web/vendor/datatable.category.created_at')),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(60)
                ->addClass('text-center'),
        ];
    }

    protected function filename()
    {
        return 'category_' . date('YmdHis');
    }
}
