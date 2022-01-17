<?php
namespace App\DataTables\Vendor;

use App\Http\Controllers\Controller;
use App\Models\Item;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class ItemRestaurantDataTable extends DataTable
{
    public function __construct()
    {
        Item::$show = true;
    }

    /**
     * Build DataTable class.
     *
     * @param mixed $query Results from query() method.
     * @return \Yajra\DataTables\DataTableAbstract
     */
    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('amount', static function ($query){
                return $query->amount . ' ' . __('web/vendor/datatable.item.calories');
            })
            ->editColumn('brief_desc_'.Controller::$lang, static function ($query){
                return \WEBHelper::truncate($query->{'brief_desc_'.Controller::$lang}, 30);
            })
            ->addColumn('action', static function ($query){
                return '<a href="'. route('vendor.item.show' ,$query) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                    '<a href="'. route('vendor.item.edit' ,$query) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';
            })->rawColumns([
                'action',
                'brief_desc_'.Controller::$lang,
            ]);
    }


    public function query(Item $model)
    {
        return $model
            ->newQuery()
            ->where('vendor_id', \Auth::id())
            ->with('category:id,name_'.Controller::$lang);
    }


    public function html()
    {
        return $this->builder()
            ->setTableId('item-table')
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

    /**
     * Get columns.
     *
     * @return array
     */
    protected function getColumns()
    {
        return [
            Column::make('id'),
            Column::make('name_'.Controller::$lang)->title(__('web/vendor/datatable.item.name')),
            Column::make('category.name_'.Controller::$lang)->title(__('web/vendor/datatable.item.desecration')),
            Column::make('amount')->title(__('web/vendor/datatable.item.amount')),
            Column::make('brief_desc_'.Controller::$lang)->title(__('web/vendor/datatable.item.brief_desc')),
            Column::make('optionals')->title(__('web/vendor/datatable.item.optionals')),
            Column::make('extras[].name')->title(__('web/vendor/datatable.item.extras')),
            Column::make('drinks[].name')->title(__('web/vendor/datatable.item.drinks')),
            Column::make('created_at')->title(__('web/vendor/datatable.item.created_at')),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(60)
                ->addClass('text-center'),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'items_' . date('YmdHis');
    }
}
