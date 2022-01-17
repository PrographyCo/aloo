<?php
namespace App\DataTables\Vendor\Branch;

use App\Http\Controllers\Controller;
use App\Models\Branch;
use App\Models\Item;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class ItemDataTable extends DataTable
{
    protected $branch;
    public function __construct($branch_id)
    {
        $this->branch = Branch::find($branch_id);
        Item::$show = true;
    }

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('brief_desc_'.Controller::$lang, static function ($query){
                return \WEBHelper::truncate($query->{'brief_desc_'.Controller::$lang}, 50);
            })
            ->addColumn('amount', function ($query){
                return "<div class='checkbox-input'><input type='number' class='form-control' data-ajax='".route('vendor.branch.changeAmount', [$this->branch,  $query->item_id])."' data-id='$query->id' value='$query->amount' /></div>";
            })
            ->addColumn('action', static function ($query){
                return
                '<a href="'. route('vendor.item.show' ,$query->item_id) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                '<a href="'. route('vendor.item.edit' ,$query->item_id) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';
            })->rawColumns([
                'action',
                'amount',
                'brief_desc_'.Controller::$lang,
            ]);
    }


    public function query()
    {
        return $this->branch->item()->with('category');
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

    protected function getColumns()
    {
        return [
            Column::make('id'),
            Column::make('name_'.Controller::$lang)->title(__('web/vendor/datatable.item.name')),
            Column::make('category.name_'.Controller::$lang)->title(__('web/vendor/datatable.item.desecration')),
            Column::make('amount')->title(__('web/vendor/datatable.item.amount')),
            Column::make('amount_type')->title(__('web/vendor/datatable.item.amount_type')),
            Column::make('brief_desc_'.Controller::$lang)->title(__('web/vendor/datatable.item.brief_desc')),
            Column::make('created_at')->title(__('web/vendor/datatable.item.created_at')),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(100)
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
